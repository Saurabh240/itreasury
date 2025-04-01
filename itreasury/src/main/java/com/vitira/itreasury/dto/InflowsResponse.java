package com.vitira.itreasury.dto;

import java.time.LocalDateTime;

import com.vitira.itreasury.entity.InflowsDataEntity;

public class InflowsResponse {

	private Long id;
	private LocalDateTime dateTime;
	private InflowsDataEntity inflowsData;

	public InflowsResponse(Long id, LocalDateTime dateTime, InflowsDataEntity inflowsData) {
		super();
		this.id = id;
		this.dateTime = dateTime;
		this.inflowsData = inflowsData;
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

	public InflowsDataEntity getInflowsData() {
		return inflowsData;
	}

	public void setInflowsData(InflowsDataEntity inflowsData) {
		this.inflowsData = inflowsData;
	}

}