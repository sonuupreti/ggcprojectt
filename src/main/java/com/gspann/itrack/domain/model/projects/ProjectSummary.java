package com.gspann.itrack.domain.model.projects;

import java.time.LocalDate;

import com.gspann.itrack.domain.model.allocations.ResourceProjectSummary;
import com.gspann.itrack.domain.model.common.dto.Pair;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
// @Accessors(chain = true, fluent = true)
@NoArgsConstructor
// @AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "project")
public class ProjectSummary {

	private Pair<String, String> project;

	private Pair<String, String> projectType;

	public ProjectSummary(final String projectCode, final String projectName, final String projectTypeCode,
			final String projectTypeName) {
		ProjectSummary projectSummary = new ProjectSummary();
		projectSummary.project = new Pair<String, String>(projectCode, projectName);
		projectSummary.projectType = new Pair<String, String>(projectTypeCode, projectTypeName);
	}
}
