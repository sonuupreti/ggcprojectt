package com.gspann.itrack.domain.model.timesheets.vm;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gspann.itrack.common.enums.StringValuedEnum;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;

@Immutable
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TimeSheetStatusTypeVM implements StringValuedEnum {
	
	// @formatter:off
//	NEW("NEW", "New Timesheet"), 
	PENDING_SUBMISSION("PENDING_SUBMISSION", "Pending Submission"), 
	SAVED("SAVED", "Saved Timesheet"),
	SUBMITTED("SUBMITTED", "Submitted Timesheet"),
	APPROVED("APPROVED", "Approved Timesheet"),
	REJECTED("REJECTED", "Rejected Timesheet"),
	PARTIALLY_APPROVED_REJECTED("PARTIALLY_APPROVED_REJECTED", "Approved for some projects but rejected for others"),
	PARTIALLY_APPROVED_PENDING("PARTIALLY_APPROVED_PENDING", "Approved for some projects but pending for approval/rejection for others"),
	PARTIALLY_REJECTED_PENDING("PARTIALLY_REJECTED_PENDING", "Rejected for some projects but pending for approval/rejection for others"),
	; 
	// @formatter:on
	
	private String code;
	
	private String description;

	private TimeSheetStatusTypeVM(final String code, final String description) {
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public String value() {
		return this.description;
	}

	@Override
	public TimeSheetStatusTypeVM enumByValue(String description) {
		for (TimeSheetStatusTypeVM e : values())
			if (e.value().equals(description))
				return e;
		throw new IllegalArgumentException();
	}

	public TimeSheetStatusTypeVM enumByCode(String code) {
		for (TimeSheetStatusTypeVM e : values())
			if (e.getCode().equals(code))
				return e;
		throw new IllegalArgumentException();
	}
	
	public static TimeSheetStatusTypeVM from(final TimesheetStatus status) {
		return valueOf(status.name());
	}
	
	public static void main(String[] args) {
		System.out.println(from(TimesheetStatus.SAVED));
		System.out.println(from(TimesheetStatus.SUBMITTED));
		System.out.println(from(TimesheetStatus.APPROVED));
		System.out.println(from(TimesheetStatus.REJECTED));
	}
	
	@JsonIgnore
	public boolean isPendingForsubmission() {
		return this == TimeSheetStatusTypeVM.PENDING_SUBMISSION;
	}

	@JsonIgnore
	public boolean isSaved() {
		return this == TimeSheetStatusTypeVM.SAVED;
	}

	@JsonIgnore
	public boolean isSubmitted() {
		return this == TimeSheetStatusTypeVM.SUBMITTED;
	}

	@JsonIgnore
	public boolean isApproved() {
		return this == TimeSheetStatusTypeVM.APPROVED;
	}

	@JsonIgnore
	public boolean isRejected() {
		return this == TimeSheetStatusTypeVM.REJECTED;
	}
}
