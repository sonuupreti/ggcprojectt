package com.gspann.itrack.domain.model.org.holidays;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum HolidayType implements StringValuedEnum {

	//@formatter:off
	REGIONAL("RH", "Regional Holiday"),
	NATIONAL("NH", "National Holiday");
	//@formatter:on

	private final String value;
	
	private final String description;

	private HolidayType(final String value, final String description) {
		this.value = value;
		this.description = description;
	}

	@Override
	public String value() {
		return this.value;
	}

	public String description() {
		return this.description;
	}
	
	@Override
	public StringValuedEnum enumByValue(String description) {
		for (HolidayType e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}
}
