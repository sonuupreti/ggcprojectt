package com.gspann.itrack.domain.model.business.payments;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum PayRateUnit implements StringValuedEnum {

	//@formatter:off
	HOURLY("Hourly"),
	DAILY("Daily"),
	WEEKLY("Weekly"),
	MONTHLY("Monthly"),
	YEARLY("Yearly");
	//@formatter:on

	private final String description;

	private PayRateUnit(final String description) {
		this.description = description;
	}

	@Override
	public String value() {
		return this.description;
	}

	@Override
	public StringValuedEnum enumByValue(String description) {
		for (PayRateUnit e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}
}
