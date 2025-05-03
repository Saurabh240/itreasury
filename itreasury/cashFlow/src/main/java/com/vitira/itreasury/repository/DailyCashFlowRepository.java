package com.vitira.itreasury.repository;

import com.vitira.itreasury.entity.DailyCashFlowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyCashFlowRepository extends JpaRepository<DailyCashFlowEntity, Long> {
    List<DailyCashFlowEntity> findByDate(LocalDate date);
    List<DailyCashFlowEntity> findByDateBetween(LocalDate start, LocalDate end);
}