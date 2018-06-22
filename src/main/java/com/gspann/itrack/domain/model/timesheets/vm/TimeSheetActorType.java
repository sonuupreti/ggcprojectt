package com.gspann.itrack.domain.model.timesheets.vm;

import com.gspann.itrack.common.enums.StringValuedEnum;

public enum TimeSheetActorType implements StringValuedEnum {
	
	// @formatter:off
	RESOURCE("RESOURCE", "Timesheet Owner"), 
	APPROVER("APPROVER", "Timesheet Approver"); 
	// @formatter:on
	
	private String value;
	
	private String description;

	private TimeSheetActorType(final String value, final String description) {
		this.value = value;
		this.description = description;
	}

	public String description() {
		return this.description;
	}
	
	@Override
	public String value() {
		return this.value;
	}

	@Override
	public TimeSheetActorType enumByValue(String value) {
		for (TimeSheetActorType e : values())
			if (e.value().equals(value))
				return e;
		throw new IllegalArgumentException();
	}
}
