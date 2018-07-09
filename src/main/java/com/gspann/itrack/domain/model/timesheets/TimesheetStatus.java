package com.gspann.itrack.domain.model.timesheets;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum TimesheetStatus implements StringValuedEnum {
	
	// @formatter:off
	SAVED("SAVED"), 
	SUBMITTED("SUBMITTED"), 
	APPROVED("APPROVED"), 
	REJECTED("REJECTED"), 
	PARTIALLY_APPROVED_REJECTED("PARTIALLY_APPROVED_REJECTED"),
	PARTIALLY_APPROVED_PENDING("PARTIALLY_APPROVED_PENDING"),
	PARTIALLY_REJECTED_PENDING("PARTIALLY_REJECTED_PENDING")
//	ADJUSTMENTS_REQUESTED("ADJUSTMENTS_REQUESTED"),
//	ADJUSTMENTS_REJECTED("ADJUSTMENTS_REJECTED"),
//	CANCELLED("CANCELLED")
	; 
	// @formatter:on
	
	private String value;

	private TimesheetStatus(final String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public StringValuedEnum enumByValue(String description) {
		for (TimesheetStatus e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}
}
