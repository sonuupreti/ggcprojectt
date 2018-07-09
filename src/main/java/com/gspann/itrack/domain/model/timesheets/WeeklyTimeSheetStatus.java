package com.gspann.itrack.domain.model.timesheets;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.common.constants.ApplicationConstant;
import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AccessLevel;
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
@EqualsAndHashCode(of = { "status" }, callSuper = false)
@ToString(includeFieldNames = true, exclude = { "projectTimeSheetStatuses", "nonLeaveProjectTimeSheetStatuses",
		"leaveProjectTimeSheetStatuses" })
@Entity
@Table(name = "WEEKLY_TIME_SHEET_STATUSES")
public class WeeklyTimeSheetStatus extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS", nullable = false)
	private TimesheetStatus status;

	@Column(name = "COMMENTS", nullable = true, length = 255)
	private String comments;

	@Column(name = "UPDATED_ON", nullable = false)
//	@Column(name = "UPDATED_ON", insertable = false, updatable = false, nullable = false)
//	@org.hibernate.annotations.Generated(org.hibernate.annotations.GenerationTime.ALWAYS)
//	@UpdateTimestamp
	private ZonedDateTime updatedOn;

	@ElementCollection(targetClass = ProjectTimeSheetStatus.class, fetch = FetchType.EAGER)
	// @formatter:off
	@CollectionTable(name = "PROJECT_TIME_SHEET_STATUSES", 
		indexes = { @Index(name = IDX_PROJECT_TIME_SHEET_STATUSES_PROJECT_CODE, columnList = "PROJECT_CODE")},
		joinColumns = { @JoinColumn(name = "WEEKLY_TIME_SHEET_STATUS_ID", referencedColumnName = "ID") },
		foreignKey = @ForeignKey(name = FK_PROJECT_TIME_SHEET_STATUSES_WEEKLY_TIME_SHEET_STATUS_ID))
	@MapKeyJoinColumn(name = "PROJECT_CODE", referencedColumnName = "CODE", 
			foreignKey = @ForeignKey(name = FK_PROJECT_TIME_SHEET_STATUSES_PROJECT_CODE))
	// @formatter:on
	@Getter(value = AccessLevel.NONE)
	private Map<Project, ProjectTimeSheetStatus> projectTimeSheetStatuses = new HashMap<Project, ProjectTimeSheetStatus>();

	@Transient
	@Getter(value = AccessLevel.NONE)
	private Map<Project, ProjectTimeSheetStatus> nonLeaveProjectTimeSheetStatuses;

	@Transient
	@Getter(value = AccessLevel.NONE)
	private Map<Project, ProjectTimeSheetStatus> leaveProjectTimeSheetStatuses;

	public boolean containsLeaveTypeProject() {
		if(this.projectTimeSheetStatuses != null && !this.projectTimeSheetStatuses.isEmpty()) {
			return projectTimeSheetStatuses.keySet().stream().anyMatch((project) -> project.isLeaveType());
		} else {
			return false;
		}
	}

	public Map<Project, ProjectTimeSheetStatus> projectTimeSheetStatuses() {
		return Collections.unmodifiableMap(projectTimeSheetStatuses);
	}
	
	public void clearProjectStatuses() {
		if(this.projectTimeSheetStatuses != null && !this.projectTimeSheetStatuses.isEmpty()) {
			this.projectTimeSheetStatuses.clear();
			this.nonLeaveProjectTimeSheetStatuses = null;
			this.leaveProjectTimeSheetStatuses = null;
		}
	}

	public Map<Project, ProjectTimeSheetStatus> nonLeaveProjectTimeSheetStatuses() {
		if (this.nonLeaveProjectTimeSheetStatuses == null) {
			if (containsLeaveTypeProject()) {
				this.nonLeaveProjectTimeSheetStatuses = projectTimeSheetStatuses;
				this.nonLeaveProjectTimeSheetStatuses.keySet().removeIf(project -> project.isLeaveType());
			} else {
				this.nonLeaveProjectTimeSheetStatuses = projectTimeSheetStatuses;
			}
		}
		return Collections.unmodifiableMap(this.nonLeaveProjectTimeSheetStatuses);
	}

	public Map<Project, ProjectTimeSheetStatus> leaveProjectTimeSheetStatuses() {
		if (this.leaveProjectTimeSheetStatuses == null) {
			if (containsLeaveTypeProject()) {
				this.leaveProjectTimeSheetStatuses = projectTimeSheetStatuses;
				this.leaveProjectTimeSheetStatuses.keySet().removeIf(project -> !project.isLeaveType());
			} else {
				this.leaveProjectTimeSheetStatuses = projectTimeSheetStatuses;
			}
		}
		return Collections.unmodifiableMap(this.leaveProjectTimeSheetStatuses);
	}

	private static WeeklyTimeSheetStatus newInstance() {
		return new WeeklyTimeSheetStatus();
	}

	public static WeeklyTimeSheetStatus forSave(Set<Project> projects) {
		WeeklyTimeSheetStatus weeklyTimeSheetStatus = newInstance();
		weeklyTimeSheetStatus.status = TimesheetStatus.SAVED;
		weeklyTimeSheetStatus.updatedOn = ZonedDateTime.now(ApplicationConstant.DEFAULT_TIME_ZONE);
		projects.forEach((project) -> {
			if (project.isLeaveType()) {
				weeklyTimeSheetStatus.projectTimeSheetStatuses.put(project,
						ProjectTimeSheetStatus.forApprove(null, "Auto Approved"));
			} else {
				weeklyTimeSheetStatus.projectTimeSheetStatuses.put(project, ProjectTimeSheetStatus.forSave());
			}
		});
		return weeklyTimeSheetStatus;
	}

	public static WeeklyTimeSheetStatus forSubmit(Set<Project> projects) {
		WeeklyTimeSheetStatus weeklyTimeSheetStatus = newInstance();
		weeklyTimeSheetStatus.status = TimesheetStatus.SUBMITTED;
		weeklyTimeSheetStatus.updatedOn = ZonedDateTime.now(ApplicationConstant.DEFAULT_TIME_ZONE);
		projects.forEach((project) -> {
			if (project.isLeaveType()) {
				weeklyTimeSheetStatus.projectTimeSheetStatuses.put(project,
						ProjectTimeSheetStatus.forApprove(null, "Auto Approved"));
			} else {
				weeklyTimeSheetStatus.projectTimeSheetStatuses.put(project, ProjectTimeSheetStatus.forSubmit());
			}
		});
		return weeklyTimeSheetStatus;
	}

	// public static WeeklyTimeSheetStatus forApprove(final Project project, final
	// String comments) {
	// WeeklyTimeSheetStatus weeklyTimeSheetStatus = newInstance();
	// weeklyTimeSheetStatus.projectTimeSheetStatuses.put(project,
	// ProjectTimeSheetStatus.forApprove(comments));
	// weeklyTimeSheetStatus.updateConsolidatedStatus();
	// return weeklyTimeSheetStatus;
	// }
	//
	// public static WeeklyTimeSheetStatus forReject(final Project project, final
	// String comments) {
	// WeeklyTimeSheetStatus weeklyTimeSheetStatus = newInstance();
	// weeklyTimeSheetStatus.projectTimeSheetStatuses.put(project,
	// ProjectTimeSheetStatus.forReject(comments));
	// weeklyTimeSheetStatus.updateConsolidatedStatus();
	// return weeklyTimeSheetStatus;
	// }

	public void approve(final Resource approver, final Project project, final String comments) {
		this.nonLeaveProjectTimeSheetStatuses.get(project).approve(approver, comments);
		this.updatedOn = ZonedDateTime.now(ApplicationConstant.DEFAULT_TIME_ZONE);
		updateConsolidatedStatus();
	}

	public void reject(final Resource approver, final Project project, final String comments) {
		this.nonLeaveProjectTimeSheetStatuses.get(project).reject(approver, comments);
		this.updatedOn = ZonedDateTime.now(ApplicationConstant.DEFAULT_TIME_ZONE);
		updateConsolidatedStatus();
	}

	private void updateConsolidatedStatus() {
		Set<TimesheetStatus> projectStatuses = nonLeaveProjectTimeSheetStatuses().values().stream()
				.map(ProjectTimeSheetStatus::projectWiseStatus).collect(Collectors.toSet());
		if (projectStatuses.size() == 1) {
			this.status = projectStatuses.iterator().next();
		} else {
			// TODO: Add conditions for more status values
			this.status = TimesheetStatus.PARTIALLY_APPROVED_REJECTED;
		}
	}
}
