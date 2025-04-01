package com.vitira.itreasury.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitira.itreasury.entity.MT940Message;

public interface MT940MessageRepository extends JpaRepository<MT940Message, Long> {
}
