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
@Table(name = "ENGAGEMENT_STATUSES", uniqueConstraints = @UniqueConstraint(name = "UNQ_ENG_STATUS", columnNames = { "ENG_STATUS"}))
public class EngagementStatus extends AbstractIdentifiable<EngagementStatus, Short> {

//    Active, Did Not Join, Terminated, On Long leave, On Bench, Pending, Resigned, Absconding

	@NotNull
    @Column(name = "ENG_STATUS", nullable = false, length = 50)
    private String statusCode;
	
	@NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description;
}
