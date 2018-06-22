package com.gspann.itrack.domain.model.common.dto;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TimeSheetStatusType implements StringValuedEnum {
	
	// @formatter:off
	NEW("NEW", "New Timesheet"), 
	SAVED("SAVED", "Saved Timesheet"),
	SUBMITTED("SUBMITTED", "Submitted Timesheet"),
	APPROVED("APPROVED", "Approved Timesheet"); 
	// @formatter:on
	
	private String code;
	
	private String description;

	private TimeSheetStatusType(final String code, final String description) {
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public String value() {
		return this.description;
	}

	@Override
	public TimeSheetStatusType enumByValue(String description) {
		for (TimeSheetStatusType e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}

	public TimeSheetStatusType enumByCode(String code) {
		for (TimeSheetStatusType e : values())
			if (e.getCode().equals(code))
				return e;
		throw new IllegalArgumentException();
	}
}
