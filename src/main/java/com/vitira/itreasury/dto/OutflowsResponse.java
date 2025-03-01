package com.vitira.itreasury.dto;

import java.time.LocalDateTime;

import com.vitira.itreasury.entity.OutflowsDataEntity;

public class OutflowsResponse {

	private Long id;
    private LocalDateTime dateTime;
    private OutflowsDataEntity outflowsData;
    
    public OutflowsResponse(Long id, LocalDateTime dateTime, OutflowsDataEntity outflowsData) {
		super();
		this.id = id;
		this.dateTime = dateTime;
		this.outflowsData = outflowsData;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public OutflowsDataEntity getOutflowsData() {
        return outflowsData;
    }

    public void setOutflowsData(OutflowsDataEntity outflowsData) {
        this.outflowsData = outflowsData;
    }

}