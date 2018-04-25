package com.gspann.itrack.domain.model.timesheets;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum TimesheetStatus implements StringValuedEnum {
	
	SAVED("SAVED"), APPROVED("APPROVED"), REJECTED("REJECTED"), PARTIALLY_APPROVED("PARTIALLY_APPROVED"); 

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
