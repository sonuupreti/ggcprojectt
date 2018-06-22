package com.gspann.itrack.domain.model.timesheets.vm;

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.gspann.itrack.domain.model.timesheets.DayType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "date")
public class TimeSheetDayMetaDataVM {

	private LocalDate date;

	private DayOfWeek day;
	
	private DayType type;
	
	private String remarks;
	
	public static TimeSheetDayMetaDataVM ofWorkingDay(final LocalDate date) {
		TimeSheetDayMetaDataVM dayVM = new TimeSheetDayMetaDataVM();
		dayVM.date = date;
		dayVM.day = date.getDayOfWeek();
		dayVM.type = DayType.WORKING_DAY;
		return dayVM;
	}
	
	public static TimeSheetDayMetaDataVM ofWeekend(final LocalDate date) {
		TimeSheetDayMetaDataVM dayVM = new TimeSheetDayMetaDataVM();
		dayVM.date = date;
		dayVM.day = date.getDayOfWeek();
		dayVM.type = DayType.WEEK_END;
		return dayVM;
	}
	
	public static TimeSheetDayMetaDataVM ofHoliday(final LocalDate date, final String occassion) {
		TimeSheetDayMetaDataVM dayVM = new TimeSheetDayMetaDataVM();
		dayVM.date = date;
		dayVM.day = date.getDayOfWeek();
		dayVM.type = DayType.HOLIDAY;
		dayVM.remarks = occassion;
		return dayVM;
	}
}
