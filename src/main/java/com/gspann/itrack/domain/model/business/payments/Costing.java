package com.gspann.itrack.domain.model.business.payments;

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.type.Identifiable;

public interface Costing extends Identifiable<Long> {

	public static final int STANDARD_WORKING_HOURS_PER_WEEK = 40;

	public static final int NUMBER_OF_WEEKS_IN_A_YEAR = 52;

	public Payment costRate();

	public Money hourlyCostRate();

	public DateRange dateRange();

	public Money costToCompany();
}
