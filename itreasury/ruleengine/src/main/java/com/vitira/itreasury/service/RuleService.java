package com.vitira.itreasury.service;

import com.vitira.itreasury.config.RulesChangedEvent;
import com.vitira.itreasury.dto.RuleDto;
import com.vitira.itreasury.entity.RuleEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.vitira.itreasury.repository.RuleRepository;

import java.util.Optional;

@Service
public class RuleService {
    private final RuleRepository repo;
    private final ApplicationEventPublisher evt;
    private final ModelMapper mapper;
    public RuleService(RuleRepository repo, ApplicationEventPublisher evt, ModelMapper mapper) {
        this.repo = repo;
        this.evt = evt;
        this.mapper = mapper;
    }

    public Page<RuleDto> list(Pageable pg) {
        return repo.findAll(pg)
                .map(e -> mapper.map(e, RuleDto.class));
    }

    @Transactional
    public RuleDto get(Long id) {
        Optional<RuleEntity> optionalRuleEntity = repo.findById(id);
        RuleEntity ruleEntity = null;
        if(optionalRuleEntity.isPresent()) {
            ruleEntity = optionalRuleEntity.get();
            return mapper.map(ruleEntity, RuleDto.class);
        }
        return null;
    }

    @Transactional
    public RuleDto create(RuleDto dto) {
        RuleEntity e = repo.save(mapper.map(dto, RuleEntity.class));
        evt.publishEvent(new RulesChangedEvent(this));
        return mapper.map(e, RuleDto.class);
    }

    @Transactional
    public RuleDto update(Long id, RuleDto dto) {
        RuleEntity existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rule not found with id " + id));

        existing.setName(dto.getName());
        existing.setConditionExpression(dto.getConditionExpression());
        existing.setActive(dto.isActive());
        existing.setSequence(dto.getSequence());

        RuleEntity saved = repo.save(existing);
        evt.publishEvent(new RulesChangedEvent(this));
        return mapper.map(saved, RuleDto.class);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Rule not found with id " + id);
        }
        repo.deleteById(id);
        evt.publishEvent(new RulesChangedEvent(this));
    }
}