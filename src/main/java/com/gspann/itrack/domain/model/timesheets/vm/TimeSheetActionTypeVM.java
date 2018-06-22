package com.gspann.itrack.domain.model.timesheets.vm;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TimeSheetActionTypeVM implements StringValuedEnum {
	
	// @formatter:off
	NONE("NONE", "No Action"), 
	SAVE("SAVE", "Save Timesheet"), 
	SUBMIT("SUBMIT", "Submit Timesheet"),
	APPROVE("APPROVE", "Approve Timesheet"),
	REJECT("REJECT", "Reject Timesheet"); 
	// @formatter:on
	
	private String code;
	
	private String description;

	private TimeSheetActionTypeVM(final String code, final String description) {
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
	public TimeSheetActionTypeVM enumByValue(String description) {
		for (TimeSheetActionTypeVM e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}

	public TimeSheetActionTypeVM enumByCode(String code) {
		for (TimeSheetActionTypeVM e : values())
			if (e.getCode().equals(code))
				return e;
		throw new IllegalArgumentException();
	}
}
