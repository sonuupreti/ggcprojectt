package com.gspann.itrack.domain.model.common.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

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
public class ResourceOnBoardingDTO {
    
    @NotNull
    private String resourceCode;
    @NotNull
    private String action;
    @NotNull
    private String emailId;
    @NotNull
    private String greytHRId;
    private LocalDate actualJoiningDate;
    private String employeeStatusCode;

}
