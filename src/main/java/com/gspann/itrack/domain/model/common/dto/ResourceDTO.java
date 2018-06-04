package com.gspann.itrack.domain.model.common.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.javamoney.moneta.Money;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.staff.Gender;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResourceDTO {
	
	private String resourceCode;
	private long version;
	@NotNull
	private String emailId;
	private String greytHRId;
	@NotNull
	private String name;
	@NotNull
	private Gender gender;
	@NotNull
	private Designation designation;
	@NotNull
	private String employmentTypeCode;
	@NotNull
	private String employeeStatusCode;
	@NotNull
	private City baseLocation;
	private City deputedLocation;
	@NotNull
	private String primarySkills;
	
	private String secondarySkills;
	private LocalDate expectedJoiningDate;
	@NotNull
	private LocalDate actualJoiningDate;
	private LocalDate exitDate;
	private long imageId;
	private EmploymentType employmentType;
	private Money annualSalary;

}
