package com.gspann.itrack.domain.model.organisation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
@Entity
@Table(name = "EMPLOYMENT_STATUSES", uniqueConstraints = @UniqueConstraint(name = "UNQ_EMP_STATUS", columnNames = { "EMP_STATUS"}))
public class EmploymentStatus extends AbstractIdentifiable<EmploymentStatus, Short> {

//    FULLTIME_EMPLOYEE("FTE"),
//    DIRECT_CONTRACTOR("DC"),
//    SUB_CONTRACTOR("SC"),
//    W2("W2");
    
	@NotNull
    @Column(name = "EMP_STATUS", nullable = false, length = 50)
    private String statusCode;

	@NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description;
}
