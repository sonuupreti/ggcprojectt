package com.gspann.itrack.domain.model.timesheets;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
	
	public String getCode() {
		return this.code;
	}
	
	public String getValue() {
		return this.value;
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
			if (e.getCode().equals(code))
				return e;
		throw new IllegalArgumentException();
	}
}
