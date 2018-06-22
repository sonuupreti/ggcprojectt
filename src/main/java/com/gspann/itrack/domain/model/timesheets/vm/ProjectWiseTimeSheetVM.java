package com.gspann.itrack.domain.model.timesheets.vm;

import java.time.Duration;
import java.util.Set;
import java.util.TreeSet;

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
public class ProjectWiseTimeSheetVM {

	private long timesheetId;

	private Set<ProjectWiseTimeSheetWeekVM> projectTimeSheets = new TreeSet<>(
			(x, y) -> x.projectName().compareTo(y.projectName()));

	private Set<DailyTotalEntry> totalEntries;

	private Duration grandTotal;

	public static ProjectWiseTimeSheetVM of(final long timesheetId, 
			final Set<DailyTotalEntry> totalEntries) {
		ProjectWiseTimeSheetVM projectWiseTimeSheetVM = new ProjectWiseTimeSheetVM();
		projectWiseTimeSheetVM.timesheetId = timesheetId;
		projectWiseTimeSheetVM.totalEntries = totalEntries;
		projectWiseTimeSheetVM.grandTotal = Duration.ZERO;
		totalEntries.forEach((entry) -> projectWiseTimeSheetVM.grandTotal = projectWiseTimeSheetVM.grandTotal
				.plus(entry.getTotalHours()));
		return projectWiseTimeSheetVM;
	}

	public void addProjectWiseWeeklyTimeSheet(ProjectWiseTimeSheetWeekVM projectWiseTimeSheet) {
		this.projectTimeSheets.add(projectWiseTimeSheet);
	}

	public void addProjectWiseWeeklyTimeSheets(Set<ProjectWiseTimeSheetWeekVM> projectWiseTimeSheets) {
		this.projectTimeSheets.addAll(projectWiseTimeSheets);
	}
}
