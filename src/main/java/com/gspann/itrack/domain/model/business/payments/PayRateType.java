package com.gspann.itrack.domain.model.business.payments;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum PayRateType implements StringValuedEnum {

	//@formatter:off
	COST_RATE("Cost Rate"),
	BILL_RATE("Bill Rate");
	//@formatter:on

	private final String description;

	private PayRateType(final String description) {
		this.description = description;
	}

	@Override
	public String value() {
		return this.description;
	}

	@Override
	public StringValuedEnum enumByValue(String description) {
		for (PayRateType e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}
}
