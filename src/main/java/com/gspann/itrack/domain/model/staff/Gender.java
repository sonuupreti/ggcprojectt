package com.gspann.itrack.domain.model.staff;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum Gender implements StringValuedEnum {
	
	MALE("Male"), FEMALE("Female"), OTHER("Other"); // Like trans-gender

	private String description;

	private Gender(final String description) {
		this.description = description;
	}

	@Override
	public String value() {
		return this.description;
	}

	@Override
	public StringValuedEnum enumByValue(String description) {
		for (Gender e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}
}
