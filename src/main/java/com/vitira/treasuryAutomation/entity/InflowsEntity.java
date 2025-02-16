package com.vitira.treasuryAutomation.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InflowsEntity {
    
	private Long id;
    private LocalDateTime datetime;
    private InflowsDataEntity inflowsData;
    
    public InflowsEntity(@JsonProperty("id") Long id, @JsonProperty("datetime")LocalDateTime datetime, @JsonProperty("inflowsData") InflowsDataEntity inflowsData) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.inflowsData = inflowsData;
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

    public InflowsDataEntity getInflowsData() {
        return inflowsData;
    }

    public void setInflowsData(InflowsDataEntity inflowsData) {
        this.inflowsData = inflowsData;
    }
    
}