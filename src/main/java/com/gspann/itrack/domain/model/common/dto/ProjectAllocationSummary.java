package com.gspann.itrack.domain.model.common.dto;

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
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "project")
public class ProjectAllocationSummary {
	
	private Pair<String, String> project;
	
	private Pair<String, String> projectType;

	private short proportion;
	
	public static ProjectAllocationSummary of(final String projectCode, final String projectName, final String projectTypeCode, final String projectTypeName, final short proportion) {
		ProjectAllocationSummary summary = new ProjectAllocationSummary();
		summary.project = new Pair<String, String>(projectCode, projectName);
		summary.projectType = new Pair<String, String>(projectTypeCode, projectTypeName);
		summary.proportion = proportion;
		return summary;
	}
}
