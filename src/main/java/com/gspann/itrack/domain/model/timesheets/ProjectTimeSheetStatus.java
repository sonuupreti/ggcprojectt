package com.gspann.itrack.domain.model.timesheets;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = { "projectWiseStatus" })
@ToString(includeFieldNames = true)
@Embeddable
public class ProjectTimeSheetStatus {

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS", nullable = false)
	private TimesheetStatus projectWiseStatus;

	@Column(name = "COMMENTS", nullable = true, length = 255)
	private String comments;

	public static ProjectTimeSheetStatus forSave() {
		ProjectTimeSheetStatus projectTimeSheetStatus = new ProjectTimeSheetStatus();
		projectTimeSheetStatus.projectWiseStatus = TimesheetStatus.SAVED;
		return projectTimeSheetStatus;
	}

	public static ProjectTimeSheetStatus forSubmit() {
		ProjectTimeSheetStatus projectTimeSheetStatus = new ProjectTimeSheetStatus();
		projectTimeSheetStatus.projectWiseStatus = TimesheetStatus.SUBMITTED;
		return projectTimeSheetStatus;
	}

	public static ProjectTimeSheetStatus forApprove(String comments) {
		ProjectTimeSheetStatus projectTimeSheetStatus = new ProjectTimeSheetStatus();
		projectTimeSheetStatus.projectWiseStatus = TimesheetStatus.APPROVED;
		projectTimeSheetStatus.comments = comments;
		return projectTimeSheetStatus;
	}

	public static ProjectTimeSheetStatus forReject(String comments) {
		ProjectTimeSheetStatus projectTimeSheetStatus = new ProjectTimeSheetStatus();
		projectTimeSheetStatus.projectWiseStatus = TimesheetStatus.REJECTED;
		projectTimeSheetStatus.comments = comments;
		return projectTimeSheetStatus;
	}

	public void approve(final String comments) {
		this.projectWiseStatus = TimesheetStatus.APPROVED;
		this.comments = comments;
	}

	public void reject(final String comments) {
		this.projectWiseStatus = TimesheetStatus.REJECTED;
		this.comments = comments;
	}
}
