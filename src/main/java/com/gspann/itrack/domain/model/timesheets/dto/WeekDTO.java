package com.gspann.itrack.domain.model.timesheets.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
public class WeekDTO {
	
	@NotNull
	private LocalDate weekStartDate;

	@Getter(value = AccessLevel.NONE)
	private Set<DayDTO> dailyEntries;
	
	public Set<DayDTO> getDailyEntries() {
		Set<DayDTO> entries = new TreeSet<>((x, y) -> x.getDate().compareTo(y.getDate()));
		entries.addAll(this.dailyEntries);
		return entries;
	}
	
	public Map<LocalDate, DayDTO> dailyEntriesMap() {
		Map<LocalDate, DayDTO> dailyEntriesMap = new TreeMap<>((x, y) -> x.compareTo(y));
		for(DayDTO dailyEntry: dailyEntries) {
			dailyEntriesMap.put(dailyEntry.getDate(), dailyEntry);
		}
		return dailyEntriesMap;
	}
	
}
