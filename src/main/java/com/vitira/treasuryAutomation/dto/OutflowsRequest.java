package com.vitira.treasuryAutomation.dto;

import java.time.LocalDate;

import com.vitira.treasuryAutomation.entity.OutflowsDataEntity;


public class OutflowsRequest {
	private Long id;
    private LocalDate date;
    private OutflowsDataEntity outflowsData;
    
 // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OutflowsDataEntity getOutflowsData() {
        return outflowsData;
    }

    public void setOutflowsData(OutflowsDataEntity outflowsData) {
        this.outflowsData = outflowsData;
    }
    
}