package com.gspann.itrack.domain.model.project;

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
//@Table(name = "PROJECT_TYPE", uniqueConstraints = @UniqueConstraint(name = "UNQ_PRJ_TYPE", columnNames = { "TYPE"}))
public class ProjectType implements Assignable<String> {
	
	// Fix Bid, T&M, Milestone, Investment

	@NotNull
    @Column(name = "TYPE_CODE", nullable = false, length = 50)
    private String code;
	
	@NotNull
    @Column(name = "TYPE", nullable = false, length = 50)
    private String type;

	@NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description;
}
