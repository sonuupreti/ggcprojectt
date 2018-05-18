package com.gspann.itrack.domain.model.common.dto;

import java.util.LinkedHashSet;
import java.util.Set;

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
@AllArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode(of = "resource")
public class ResourceAllocationSummary {

	private Pair<String, String> resource;

	private Set<ProjectAllocationSummary> projects = new LinkedHashSet<>(5);
	
	public static ResourceAllocationSummary of(final String resourceCode, final String resourceName) {
		ResourceAllocationSummary summary = new ResourceAllocationSummary();
		summary.resource = new Pair<String, String>(resourceCode, resourceName);
		return summary;
	}
	
	public ResourceAllocationSummary addProjectAllocation(final ProjectAllocationSummary projectAllocationSummary) {
		this.projects.add(projectAllocationSummary);
		return this;
	}
}
