package com.vitira.itreasury.repository;

import com.vitira.itreasury.entity.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
    List<RuleEntity> findByActiveTrueOrderBySequenceAsc();
}