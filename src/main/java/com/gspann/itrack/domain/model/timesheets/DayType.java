package com.gspann.itrack.domain.model.timesheets;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum DayType implements StringValuedEnum {
	
	// @formatter:off
	WORKING_DAY("WD", "Regular Working Day"), 
	HOLIDAY("HD", "Holiday"),
	WEEK_END("WE", "Weekend"); 
	// @formatter:on
	
	private String code;
	
	private String value;

	private DayType(final String code, final String value) {
		this.code = code;
		this.value = value;
	}
	
	public String code() {
		return this.code;
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

	public StringValuedEnum enumByCode(String code) {
		for (DayType e : values())
			if (e.code().equals(code))
				return e;
		throw new IllegalArgumentException();
	}
}
