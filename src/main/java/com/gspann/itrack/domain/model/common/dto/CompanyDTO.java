package com.gspann.itrack.domain.model.common.dto;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gspann.itrack.domain.model.org.structure.Department;

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
public class CompanyDTO {
	
	private Department department;
	
	@NotNull
	private int baseLocationId;
	private int deputedLocationId;;
}
