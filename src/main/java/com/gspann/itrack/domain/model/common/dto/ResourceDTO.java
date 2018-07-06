package com.gspann.itrack.domain.model.common.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.javamoney.moneta.Money;

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
// @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResourceDTO {

    private String resourceCode;
    private long version;
    private String emailId;
    private String greytHRId;
    @NotNull
    private String name;
    @NotNull
    private Gender gender;
    @NotNull
    private short designationId;
    @NotNull
    private String employmentTypeCode;

    private String employeeStatusCode;
    @NotNull
    private int baseLocationId;
    private int deputedLocationId;
    @NotNull
    private String primarySkills;

    private String secondarySkills;
    private String practice;
    @NotNull
    private LocalDate expectedJoiningDate;
    private LocalDate actualJoiningDate;
    private LocalDate exitDate;
    private long imageId;
    private short companyId;
    private short departmentId;

    private Money annualSalary;
    private Money comission;
    private Money bonus;
    private Money otherCost;
    private Money rateperHour;
    
    private LocalDate paystartDate;
    private LocalDate payendDate;
    
}
