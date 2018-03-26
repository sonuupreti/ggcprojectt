package com.gspann.itrack.domain.model.staff;

import com.gspann.itrack.common.enums.StringValuedEnum;

public enum Gender implements StringValuedEnum {
	
	MALE("Male"), FEMALE("Female"), OTHER("Other"); // Like transgender

	private String value;

	private Gender(final String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public StringValuedEnum enumByValue(String value) {
		for (Gender e : values())
			if (e.value().equals(value))
				return e;
		throw new IllegalArgumentException();
	}

}
