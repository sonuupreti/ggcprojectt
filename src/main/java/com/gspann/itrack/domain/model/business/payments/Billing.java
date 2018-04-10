package com.gspann.itrack.domain.model.business.payments;

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.common.DateRange;

public interface Billing {

	public Payment payment();

	public DateRange dateRange();

	public Money hourlyPayMoney();

	public BillabilityStatus billabilityStatus();
}
