package com.gspann.itrack.domain.model.timesheets.vm;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import java.time.Duration;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gspann.itrack.domain.model.allocations.ResourceProjectSummary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectWiseTimeSheetWeekVM {

	@JsonInclude(NON_NULL)
	private ResourceProjectSummary projectDetails;

	private Set<ProjectWiseDailyEntryVM> dailyEntries = new TreeSet<>((x, y) -> x.getDate().compareTo(y.getDate()));

	private Duration projectTotalHours;

	@JsonInclude(NON_NULL)
	private TimeSheetStatusTypeVM projectTimesheetStatus;

	@JsonInclude(NON_NULL)
	private TimeSheetActionTypeVM[] actions;

//	public static ProjectWiseTimeSheetWeekVM of(final ResourceProjectSummary projectDetails,
//			final TimeSheetStatusTypeVM projectTimesheetStatus) {
//		ProjectWiseTimeSheetWeekVM projectWiseTimeSheetVM = new ProjectWiseTimeSheetWeekVM();
//		projectWiseTimeSheetVM.projectDetails = projectDetails;
//		projectWiseTimeSheetVM.projectTimesheetStatus = projectTimesheetStatus;
//		if (projectTimesheetStatus == TimeSheetStatusTypeVM.SAVED) {
////			projectWiseTimeSheetVM.actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.SAVE,
////					TimeSheetActionTypeVM.SUBMIT };
//		} else if (projectTimesheetStatus == TimeSheetStatusTypeVM.SUBMITTED) {
//			projectWiseTimeSheetVM.actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.APPROVE,
//					TimeSheetActionTypeVM.REJECT };
//		}
//		// TODO: Update later, applicable action will depend or user role, 
//		// for owner there would not be any action at project level, but for approver there will be actions at project level
//		// for other statuses such as approved/rejected etc.
//		projectWiseTimeSheetVM.projectTotalHours = Duration.ZERO;
//		return projectWiseTimeSheetVM;
//	}

	public static ProjectWiseTimeSheetWeekVM of(final ResourceProjectSummary projectDetails,
			final TimeSheetStatusTypeVM projectTimesheetStatus, final TimeSheetActorType actor) {
		ProjectWiseTimeSheetWeekVM projectWiseTimeSheetVM = new ProjectWiseTimeSheetWeekVM();
		projectWiseTimeSheetVM.projectDetails = projectDetails;
		projectWiseTimeSheetVM.projectTimesheetStatus = projectTimesheetStatus;
		projectWiseTimeSheetVM.setActions(actor);
		projectWiseTimeSheetVM.projectTotalHours = Duration.ZERO;
		return projectWiseTimeSheetVM;
	}
	
	public void setActions(final TimeSheetActorType actor) {
		if (actor == TimeSheetActorType.RESOURCE) {
			if (this.projectTimesheetStatus == TimeSheetStatusTypeVM.SAVED || this.projectTimesheetStatus == TimeSheetStatusTypeVM.PENDING_SUBMISSION) {
				actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.SAVE, TimeSheetActionTypeVM.SUBMIT };
			} else if (projectTimesheetStatus == TimeSheetStatusTypeVM.SUBMITTED || projectTimesheetStatus == TimeSheetStatusTypeVM.APPROVED) {
				actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.NONE };
			}
		} else {
			if (this.projectTimesheetStatus == TimeSheetStatusTypeVM.SAVED || projectTimesheetStatus == TimeSheetStatusTypeVM.APPROVED
					|| projectTimesheetStatus == TimeSheetStatusTypeVM.REJECTED
					|| this.projectTimesheetStatus == TimeSheetStatusTypeVM.PENDING_SUBMISSION) {
				actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.NONE };
			} else if (projectTimesheetStatus == TimeSheetStatusTypeVM.SUBMITTED) {
				actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.APPROVE, TimeSheetActionTypeVM.REJECT };
			}
		}

	}

	public void addDailyEntries(Set<ProjectWiseDailyEntryVM> dailyEntries) {
		this.dailyEntries.addAll(dailyEntries);
		this.dailyEntries.forEach((entry) -> {
			this.projectTotalHours = this.projectTotalHours.plus(entry.getHours());
//			this.subStatus = entry.isApproved() ? TimeSheetStatusTypeVM.APPROVED : TimeSheetStatusTypeVM.REJECTED;
		});
	}

	public void addDailyEntry(ProjectWiseDailyEntryVM projectWiseDailyEntryVM) {
		Set<ProjectWiseDailyEntryVM> entries = new TreeSet<>((x, y) -> x.getDate().compareTo(y.getDate()));
		entries.add(projectWiseDailyEntryVM);
		this.projectTotalHours = this.projectTotalHours.plus(projectWiseDailyEntryVM.getHours());
	}
	
	public String projectName() {
		return this.projectDetails.projectName();
	}
}
