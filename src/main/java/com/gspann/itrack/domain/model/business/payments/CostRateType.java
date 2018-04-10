package com.gspann.itrack.domain.model.business.payments;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum CostRateType implements StringValuedEnum {

	//@formatter:off
	FTE_COST_RATE("FTE Cost Rate"),
	NON_FTE_COST_RATE("Non FTE Cost Rate");
	//@formatter:on

	private final String description;

	private CostRateType(final String description) {
		this.description = description;
	}

	@Override
	public String value() {
		return this.description;
	}

	@Override
	public StringValuedEnum enumByValue(String description) {
		for (CostRateType e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}
}
