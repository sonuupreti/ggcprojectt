package com.gspann.itrack.domain.model.timesheets.vm;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MonthVM {

	public static final String MONTH_FORMAT = "MMM, yy";

	public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern(MONTH_FORMAT);

	private String monthName;

	private YearMonth month;

	public static MonthVM of(final YearMonth month) {
		MonthVM monthVM = new MonthVM();
		monthVM.month = month;
		monthVM.monthName = month.format(MONTH_FORMATTER);
		return monthVM;
	}
}
