package com.vitira.treasuryAutomation.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutflowsEntity {
    
	private Long id;
    private LocalDateTime datetime;
    private OutflowsDataEntity outflowsData;
    
    public OutflowsEntity(@JsonProperty("id") Long id, @JsonProperty("datetime")LocalDateTime datetime, @JsonProperty("outflowsData") OutflowsDataEntity outflowsData) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.outflowsData = outflowsData;
	}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDate(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public OutflowsDataEntity getOutflowsData() {
        return outflowsData;
    }

    public void setOutflowsData(OutflowsDataEntity outflowsData) {
        this.outflowsData = outflowsData;
    }
    
}