package com.vitira.itreasury.dto;

import java.time.LocalDate;

import com.vitira.itreasury.entity.InflowsDataEntity;

public class InflowsRequest {
	private Long id;
	private LocalDate date;
	private InflowsDataEntity inflowsData;

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

	public InflowsDataEntity getData() {
		return inflowsData;
	}

	public void setData(InflowsDataEntity inflowsData) {
		this.inflowsData = inflowsData;
	}

}