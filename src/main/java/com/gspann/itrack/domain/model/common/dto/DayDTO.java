package com.gspann.itrack.domain.model.common.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.gspann.itrack.domain.model.timesheets.DayType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
public class DayDTO {

	private LocalDate date;

	private DayOfWeek day;
	
	private DayType type;
	
	private String remarks;
	
	public static DayDTO ofWorkingDay(final LocalDate date) {
		DayDTO dayDTO = new DayDTO();
		dayDTO.date = date;
		dayDTO.day = date.getDayOfWeek();
		dayDTO.type = DayType.WORKING_DAY;
		return dayDTO;
	}
	
	public static DayDTO ofWeekend(final LocalDate date) {
		DayDTO dayDTO = new DayDTO();
		dayDTO.date = date;
		dayDTO.day = date.getDayOfWeek();
		dayDTO.type = DayType.WEEK_END;
		return dayDTO;
	}
	
	public static DayDTO ofHoliday(final LocalDate date, final String occassion) {
		DayDTO dayDTO = new DayDTO();
		dayDTO.date = date;
		dayDTO.day = date.getDayOfWeek();
		dayDTO.type = DayType.HOLIDAY;
		dayDTO.remarks = occassion;
		return dayDTO;
	}
}
