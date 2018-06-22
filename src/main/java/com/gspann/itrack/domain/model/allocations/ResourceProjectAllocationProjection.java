package com.gspann.itrack.domain.model.common.dto;

import com.gspann.itrack.domain.model.location.City;

import lombok.AllArgsConstructor;
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
public class ResourceProjectAllocationSummary {

	private String resourceCode;

	private String resourceName;

	private City deputedLocation;
	
	private String projectCode;
	
	private String projectName;
	
	private String projectTypeCode;

	private String projectTypeName;

	private short proportion;

	private boolean customerTimeTracking;
}
