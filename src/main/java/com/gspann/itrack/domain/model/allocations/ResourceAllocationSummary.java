package com.gspann.itrack.domain.model.allocations;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.location.City;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode(of = "resource")
public class ResourceAllocationSummary {

	private Pair<String, String> resource;

	private Set<ResourceProjectSummary> projects = new LinkedHashSet<>(5);
	
	@JsonIgnore
	private Map<String, ResourceProjectSummary> projectsMap = new HashMap<>();
	
	private City deputedLocation;
	
	public static ResourceAllocationSummary of(final String resourceCode, final String resourceName, final City deputedLocation) {
		ResourceAllocationSummary summary = new ResourceAllocationSummary();
		summary.resource = new Pair<String, String>(resourceCode, resourceName);
		summary.deputedLocation = deputedLocation;
		return summary;
	}
	
	public ResourceAllocationSummary addProjectAllocation(final ResourceProjectSummary projectSummary) {
		this.projects.add(projectSummary);
		this.projectsMap.put(projectSummary.getProject().getKey(), projectSummary);
		return this;
	}
	
	public ResourceProjectSummary getProjectSummaryByProjectCode(final String projectCode) {
		return this.projectsMap.get(projectCode);
	}
}
