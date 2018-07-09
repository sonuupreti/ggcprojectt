package com.gspann.itrack.adapter.rest.timesheet;

import static com.gspann.itrack.common.constants.RestConstant.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.gspann.itrack.ItrackApplication;
import com.gspann.itrack.domain.model.timesheets.vm.MonthVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResource;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResourceList;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVM;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TimeSheetLinks {

	static final String TIMESHEET = ITRACK_API + SLASH + "timesheets";

	static final String TIMESHEET_REL = ItrackApplication.CURIE_NAMESPACE + ":timesheets";

	static final String TIMESHEET_NEXT_WEEK_REL = "next_week";

	static final String TIMESHEET_PREVIOUS_WEEK_REL = "previous_week";

	static final String TIMESHEET_NEXT_MONTH_REL = "next_month";

	static final String TIMESHEET_PREVIOUS_MONTH_REL = "previous_month";

	static final String TIMESHEET_SAVE_REL = "save";

	static final String TIMESHEET_SUBMIT_REL = "submit";

	static final String TIMESHEET_APPROVE_REL = "approve";

	static final String TIMESHEET_REJECT_REL = "reject";

	static final String TIMESHEET_WEEKLY = SLASH + "weekly";

	static final String TIMESHEET_MONTHLY = SLASH + "monthly";

	static final String TIMESHEET_PENDING_SELF_BY_MONTH = SLASH + "pendingByMonth";

	static final String TIMESHEET_PENDING_SELF_SINCE_NO_OF_MONTHS = SLASH + "pendingSince";

	static final String TIMESHEET_APPROVER = "approver";

	static final String TIMESHEET_PENDING_FOR_APPROVER_SINCE_NO_OF_MONTHS = SLASH + TIMESHEET_APPROVER + SLASH
			+ "pendingSince";

	// static final String TIMESHEET_FOR_SELF_BY_MONTH = "forSelfByMonth";

	static final String ENTITY_NAME = "timesheet";

	@NonNull
	private final EntityLinks entityLinks;

	List<Link> getLinks(final TimeSheetResource timesheet) throws URISyntaxException {
		List<Link> links = new ArrayList<Link>();
		links.add(getSelfLink(timesheet));
		if (timesheet.getNextWeek() != null) {
			links.add(getNavigationLink(timesheet.getNextWeek(), TIMESHEET_NEXT_WEEK_REL));
		}
		if (timesheet.getPreviousWeek() != null) {
			links.add(getNavigationLink(timesheet.getPreviousWeek(), TIMESHEET_PREVIOUS_WEEK_REL));
		}
		links.addAll(getActionLinks(timesheet));
		return links;
	}

	Link getSelfLink(final TimeSheetWeekStatusVM timeSheetWeekStatusVM) {
		return linkTo(methodOn(TimeSheetResourceController.class).weekly(Optional.empty(), null))
				.slash(timeSheetWeekStatusVM.getWeek().getWeekStartDate()).withSelfRel();
	}

	Link getSelfLink(final TimeSheetResource timesheet) {
		if (timesheet.isPendingForsubmission()) {
			return linkTo(methodOn(TimeSheetResourceController.class).weekly(Optional.empty(), null))
					.slash(timesheet.getWeekDetails().getWeekStartDate()).withSelfRel();

		} else {
			return linkTo(methodOn(TimeSheetResourceController.class).weekly(timesheet.getTimesheetId(), null))
					.withSelfRel();
		}
	}

	Link getNavigationLink(final TimeSheetWeekStatusVM timeSheetWeekStatusVM, final String rel) {
		if (timeSheetWeekStatusVM.getStatus().isPendingForsubmission()) {
			return linkTo(methodOn(TimeSheetResourceController.class).weekly(Optional.empty(), null))
					.slash(timeSheetWeekStatusVM.getWeek().getWeekStartDate()).withRel(rel);
		} else {
			return linkTo(
					methodOn(TimeSheetResourceController.class).weekly(timeSheetWeekStatusVM.getTimesheetId(), null))
							.withRel(rel);
		}
	}

	List<Link> getActionLinks(final TimeSheetResource timesheet) throws URISyntaxException {
		List<Link> actionLinks = new ArrayList<Link>();

		if (timesheet.isPendingForsubmission() || timesheet.isSaved()) {
			actionLinks.add(linkTo(methodOn(TimeSheetResourceController.class).saveOrSubmitTimeSheet(null, null))
					.withRel(TIMESHEET_SAVE_REL));
			actionLinks.add(linkTo(methodOn(TimeSheetResourceController.class).saveOrSubmitTimeSheet(null, null))
					.withRel(TIMESHEET_SUBMIT_REL));
		} else if (timesheet.isSubmitted()) {
			// TODO: Approve and reject or none depending upon Actor type

		}
		return actionLinks;
	}

	List<Link> getLinks(final TimeSheetResourceList timesheetList) throws URISyntaxException {
		List<Link> links = new ArrayList<Link>();
		links.add(getSelfLink(timesheetList));
		if (timesheetList.getNextMonth() != null) {
			links.add(getNavigationLink(timesheetList.getNextMonth(), TIMESHEET_NEXT_MONTH_REL));
		}
		if (timesheetList.getPreviousMonth() != null) {
			links.add(getNavigationLink(timesheetList.getPreviousMonth(), TIMESHEET_PREVIOUS_MONTH_REL));
		}
		timesheetList.getTimesheets().forEach((timesheet) -> timesheet.add(getSelfLink(timesheet)));
		return links;
	}

	Link getSelfLink(final TimeSheetResourceList timesheetList) {
		return linkTo(methodOn(TimeSheetResourceController.class).listSelfTimeSheetsByMonth(null, null))
				.slash(timesheetList.getMonthDetails().getMonth().toString()).withSelfRel();
	}

	Link getNavigationLink(final MonthVM monthVM, final String rel) {
		return linkTo(methodOn(TimeSheetResourceController.class).listSelfTimeSheetsByMonth(null, null))
				.slash(monthVM.getMonth().toString()).withRel(rel);
	}

}
