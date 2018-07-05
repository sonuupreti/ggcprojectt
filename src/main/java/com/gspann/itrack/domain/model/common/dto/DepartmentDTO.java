package com.gspann.itrack.domain.model.common.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDTO {

    private Short departmentId;

    private String departmentName;

    private Set<Pair<Short, String>> designations;

}
