package com.gspann.itrack.domain.model.timesheets.vm;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gspann.itrack.domain.model.allocations.ResourceProjectSummary;
import com.gspann.itrack.domain.model.common.dto.Pair;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class TimeSheetResource extends ResourceSupport {

	@JsonInclude(NON_NULL)
	private LocalDate systemStartDate;

	@JsonInclude(NON_NULL)
	private Pair<String, String> resource;

	@JsonInclude(NON_EMPTY)
	private Set<ResourceProjectSummary> allocations = new LinkedHashSet<>(5);

	@JsonInclude(NON_NULL)
	private TimeSheetWeekMetaDataVM weekDetails;

	@JsonInclude(NON_NULL)
	private ProjectWiseTimeSheetVM timesheet;

	@JsonInclude(NON_NULL)
	private TimeSheetStatusTypeVM timesheetStatus;

	@JsonInclude(NON_NULL)
	private TimeSheetActionTypeVM[] actions;

	@JsonInclude(NON_NULL)
	private TimeSheetWeekStatusVM nextWeek;

	@JsonInclude(NON_NULL)
	private TimeSheetWeekStatusVM previousWeek;

	// @JsonInclude(NON_NULL)
	// private TimeSheetWeekStatusVM week;

	@JsonInclude(NON_NULL)
	private ZonedDateTime lastUpdatedOn;

	@JsonIgnore
	public long getTimesheetId() {
		return timesheet != null ? timesheet.getTimesheetId() : 0;
	}

	public static TimeSheetResource ofPendingForSubmission(final LocalDate systemStartDate,
			final Pair<String, String> resource, Set<ResourceProjectSummary> allocations,
			final TimeSheetWeekMetaDataVM weekDetails) {
		TimeSheetResource timeSheetVM = new TimeSheetResource();
		timeSheetVM.systemStartDate = systemStartDate;
		timeSheetVM.resource = resource;
		timeSheetVM.allocations = allocations;
		timeSheetVM.weekDetails = weekDetails;
		timeSheetVM.timesheetStatus = TimeSheetStatusTypeVM.PENDING_SUBMISSION;
		timeSheetVM.actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.SAVE, TimeSheetActionTypeVM.SUBMIT };
		return timeSheetVM;
	}

	public static TimeSheetResource ofPendingForSubmission(final TimeSheetWeekMetaDataVM weekDetails) {
		TimeSheetResource timeSheetVM = new TimeSheetResource();
		timeSheetVM.weekDetails = weekDetails;
		timeSheetVM.timesheetStatus = TimeSheetStatusTypeVM.PENDING_SUBMISSION;
		// timeSheetVM.actions = new TimeSheetActionTypeVM[] {
		// TimeSheetActionTypeVM.SAVE, TimeSheetActionTypeVM.SUBMIT };
		return timeSheetVM;
	}

	public static TimeSheetResource ofSaved(final LocalDate systemStartDate, final Pair<String, String> resource,
			Set<ResourceProjectSummary> allocations, final TimeSheetWeekMetaDataVM weekDetails,
			final ProjectWiseTimeSheetVM projectWiseTimesheet, final ZonedDateTime lastUpdatedOn) {
		TimeSheetResource timeSheetVM = new TimeSheetResource();
		timeSheetVM.systemStartDate = systemStartDate;
		timeSheetVM.resource = resource;
		timeSheetVM.allocations = allocations;
		timeSheetVM.weekDetails = weekDetails;
		timeSheetVM.timesheet = projectWiseTimesheet;
		timeSheetVM.timesheetStatus = TimeSheetStatusTypeVM.SAVED;
		timeSheetVM.actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.SAVE, TimeSheetActionTypeVM.SUBMIT };
		timeSheetVM.lastUpdatedOn = lastUpdatedOn;
		return timeSheetVM;
	}

	public static TimeSheetResource ofSubmitted(final LocalDate systemStartDate, final Pair<String, String> resource,
			final TimeSheetWeekMetaDataVM weekDetails, final ProjectWiseTimeSheetVM projectWiseTimesheet,
			final TimeSheetActorType actor, final ZonedDateTime lastUpdatedOn) {
		TimeSheetResource timeSheetVM = new TimeSheetResource();
		timeSheetVM.systemStartDate = systemStartDate;
		timeSheetVM.resource = resource;
		timeSheetVM.weekDetails = weekDetails;
		timeSheetVM.timesheet = projectWiseTimesheet;
		timeSheetVM.timesheetStatus = TimeSheetStatusTypeVM.SUBMITTED;
		timeSheetVM.actions = actor == TimeSheetActorType.RESOURCE
				? new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.NONE }
				: new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.APPROVE, TimeSheetActionTypeVM.REJECT };
		timeSheetVM.lastUpdatedOn = lastUpdatedOn;
		return timeSheetVM;
	}

	public static TimeSheetResource ofExistingTimeSheet(final TimeSheetWeekMetaDataVM weekDetails,
			final ProjectWiseTimeSheetVM projectWiseTimesheet, final TimeSheetStatusTypeVM timesheetStatus,
			final ZonedDateTime lastUpdatedOn) {
		TimeSheetResource timeSheetVM = new TimeSheetResource();
		timeSheetVM.weekDetails = weekDetails;
		timeSheetVM.timesheet = projectWiseTimesheet;
		timeSheetVM.timesheetStatus = timesheetStatus;
		// timeSheetVM.actions = actor == TimeSheetActorType.RESOURCE
		// ? new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.NONE }
		// : new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.APPROVE,
		// TimeSheetActionTypeVM.REJECT };
		timeSheetVM.lastUpdatedOn = lastUpdatedOn;
		return timeSheetVM;
	}

	@JsonIgnore
	public boolean isPendingForsubmission() {
		return this.timesheetStatus == TimeSheetStatusTypeVM.PENDING_SUBMISSION;
	}

	@JsonIgnore
	public boolean isSaved() {
		return this.timesheetStatus == TimeSheetStatusTypeVM.SAVED;
	}

	@JsonIgnore
	public boolean isSubmitted() {
		return this.timesheetStatus == TimeSheetStatusTypeVM.SUBMITTED;
	}

	@JsonIgnore
	public boolean isApproved() {
		return this.timesheetStatus == TimeSheetStatusTypeVM.APPROVED;
	}

	@JsonIgnore
	public boolean isRejected() {
		return this.timesheetStatus == TimeSheetStatusTypeVM.REJECTED;
	}
}
