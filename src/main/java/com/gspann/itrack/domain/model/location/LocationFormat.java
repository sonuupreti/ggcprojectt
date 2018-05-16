package com.gspann.itrack.domain.model.location;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum LocationFormat implements StringValuedEnum {
	
	// @formatter:off
	CITY_STATE_COUNTRY_NAMES_HYPHEN_SEPERATED("%1$s - %2$s - %3$s"), 
	CITY_STATE_COUNTRY_NAMES_COMMA_SEPERATED("%1$s , %2$s , %3$s"), 
	COUNTRY_STATE_CITY_NAMES_HYPHEN_SEPERATED("%3$s - %2$s - %1$s"),
	COUNTRY_STATE_CITY_NAMES_COMMA_SEPERATED("%3$s , %2$s , %1$s"); 
	// @formatter:on
	
	private String format;

	private LocationFormat(final String format) {
		this.format = format;
	}

	@Override
	public String value() {
		return this.format;
	}

	@Override
	public StringValuedEnum enumByValue(String format) {
		for (LocationFormat e : values())
			if (e.value().equals(format))
				return e;
		throw new IllegalArgumentException();
	}
}
