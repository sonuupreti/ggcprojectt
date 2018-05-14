package com.gspann.itrack.domain.model.timesheets;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum DayType implements StringValuedEnum {
	
	// @formatter:off
	WORKING_DAY("Regular Working Day"), 
	HOLIDAY("Global Holiday"),
	WEEK_END("Weekend"); 
	// @formatter:on
	
	private String value;

	private DayType(final String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public StringValuedEnum enumByValue(String description) {
		for (DayType e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}
}
