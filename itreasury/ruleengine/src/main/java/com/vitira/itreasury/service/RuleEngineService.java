package com.vitira.itreasury.service;

import com.vitira.itreasury.config.RulesChangedEvent;
import com.vitira.itreasury.entity.CashFlowEntry;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import com.vitira.itreasury.repository.RuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RuleEngineService implements ApplicationListener<RulesChangedEvent> {

    private final RuleRepository repo;
    private final ExpressionParser parser = new SpelExpressionParser();
    private volatile List<CompiledRule> activeRules = List.of();

    public RuleEngineService(RuleRepository repo) {
        this.repo = repo;
        reloadRules();
    }

    @Override
    public void onApplicationEvent(RulesChangedEvent evt) {
        reloadRules();
    }

    private void reloadRules() {
        this.activeRules = repo.findByActiveTrueOrderBySequenceAsc().stream()
                .map(r -> CompiledRule.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .sequence(r.getSequence())
                        .condition(parser.parseExpression(r.getConditionExpression()))
                        .action(parser.parseExpression(r.getActionExpression()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<CashFlowEntry> applyRules(List<CashFlowEntry> entries) {
        List<CashFlowEntry> out = new ArrayList<>();

        for (CashFlowEntry entry : entries) {
            StandardEvaluationContext ctx = new StandardEvaluationContext(entry);

            ctx.setVariable("invoiceDate",   entry.getInvoiceDate());
            ctx.setVariable("source",        entry.getSource());
            ctx.setVariable("docCurrValue",  entry.getDocCurrValue());
            ctx.setVariable("supplierCode",  entry.getSupplierCode());
            ctx.setVariable("amount",        entry.getAmount());
            ctx.setVariable("dueDate",       entry.getDueDate());

            boolean matchedAny = false;

            for (CompiledRule rule : activeRules) {
                try {
                    Boolean hit = rule.getCondition().getValue(ctx, Boolean.class);
                    if (Boolean.TRUE.equals(hit)) {
                        rule.getAction().getValue(ctx, Void.class);
                        log.info("Rule {} fired on entry {} → {}",
                                rule.getName(), entry.getId(), entry.getCategoryType());
                        matchedAny = true;
                        break;
                    }
                } catch (RuntimeException ex) {
                    log.error("Error in rule {} on entry {}: {}",
                            rule.getName(), entry.getId(), ex.getMessage(), ex);
                }
            }

            if (matchedAny) {
                out.add(entry);
            }
        }

        return out;
    }


    @Data
    @Builder
    private static class CompiledRule {
        Long         id;
        String       name;
        Expression   condition;
        Expression   action;
        Integer      sequence;
    }
}
