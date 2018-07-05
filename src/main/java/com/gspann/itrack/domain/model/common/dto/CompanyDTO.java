package com.gspann.itrack.domain.model.common.dto;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

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
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CompanyDTO {

    private Short companyId;

    private String companyName;

    private Set<DepartmentDTO> departments;

}
