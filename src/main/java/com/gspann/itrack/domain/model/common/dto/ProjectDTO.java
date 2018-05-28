package com.gspann.itrack.domain.model.common.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.org.structure.Practice;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
// @Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProjectDTO {

	private String projectCode;

	@NotNull
	private String projectName;

	@NotNull
	private String projectTypeCode;
	private String projectTypeDescription;

	private String projectStatusCode;
	private String projectStatusDescription;

	List<Pair<String, String>> practiceList;
	
	@NotNull
	private String customerManager;
	
	private String customerProjectId;
	private String customerProjectName;
	
	private int cityId;
	private String location;

	@NotNull
	private String technologies;

	@NotNull
	private String offshoreManagerCode;

	@NotNull
	private String onshoreManagerCode;

	@NotNull
	private String offshoreManagerName;

	@NotNull
	private String onshoreManagerName;

	private String accountCode;
	private String accountName;
	
	private LocalDate startDate; 
	private LocalDate endDate; 

}