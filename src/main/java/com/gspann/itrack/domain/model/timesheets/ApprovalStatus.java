package com.gspann.itrack.domain.model.timesheets;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
public enum ApprovalStatus implements StringValuedEnum {
	
	APPROVED("APPROVED"), REJECTED("REJECTED"), PARTIAL("PARTIAL"); 

	private String value;

	private ApprovalStatus(final String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public StringValuedEnum enumByValue(String description) {
		for (ApprovalStatus e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}
}
