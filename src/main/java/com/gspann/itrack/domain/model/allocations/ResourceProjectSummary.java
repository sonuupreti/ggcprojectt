package com.gspann.itrack.domain.model.allocations;

import java.time.LocalDate;

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
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "project")
public class ResourceProjectSummary {

	private Pair<String, String> project;

	private Pair<String, String> projectType;

	private LocalDate fromDate;

	private LocalDate tillDate;

	private short proportion;

	private boolean customerTimeTracking;

	public static ResourceProjectSummary of(final String projectCode, final String projectName, final String projectTypeCode,
			final String projectTypeName, final LocalDate fromDate, final LocalDate tillDate, final short proportion,
			final boolean customerTimeTracking) {
		ResourceProjectSummary summary = new ResourceProjectSummary();
		summary.project = new Pair<String, String>(projectCode, projectName);
		summary.projectType = new Pair<String, String>(projectTypeCode, projectTypeName);
		summary.fromDate = fromDate;
		summary.tillDate = tillDate;
		summary.proportion = proportion;
		summary.customerTimeTracking = customerTimeTracking;
		return summary;
	}
	
	public String projectCode() {
		return this.project.getKey();
	}
	
	public String projectName() {
		return this.project.getValue();
	}
}
