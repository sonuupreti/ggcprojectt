package com.gspann.itrack.domain.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.Assignable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
//@Entity
//@Table(name = "BILLABILITY_STATUSES", uniqueConstraints = @UniqueConstraint(name = "UNQ_EMP_STATUS", columnNames = { "BILL_STATUS"}))
public class BillabilityStatus implements Assignable<String> {

//	BILLABLE, NON_BILLABLE, BUFFER 
    
	@NotNull
    @Column(name = "BILL_STATUS", nullable = false, length = 50)
    private String code;

	@NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description;
}
