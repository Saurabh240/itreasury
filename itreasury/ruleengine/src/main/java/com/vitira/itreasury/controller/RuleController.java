package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.RuleDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.vitira.itreasury.service.RuleService;

@RestController
@RequestMapping("/rules")
public class RuleController {
    private final RuleService svc;

    public RuleController(RuleService svc) {
        this.svc = svc;
    }

    @GetMapping
    public Page<RuleDto> list(Pageable pg) {
        return svc.list(pg);
    }

    @PostMapping
    public RuleDto create(@RequestBody @Valid RuleDto dto) {
        return svc.create(dto);
    }

    @PutMapping("/{id}")
    public RuleDto update(@PathVariable Long id,
                          @RequestBody @Valid RuleDto dto) {
        return svc.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}
