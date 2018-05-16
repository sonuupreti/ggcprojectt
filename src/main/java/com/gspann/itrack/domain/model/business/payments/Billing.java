package com.gspann.itrack.domain.model.business.payments;

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.model.common.DateRange;

public interface Billing {

	public Payment billRate();

	public Money hourlyBillRate();
	
	public DateRange dateRange();

	public BillabilityStatus billabilityStatus();
}
