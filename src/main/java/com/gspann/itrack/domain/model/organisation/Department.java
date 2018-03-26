package com.gspann.itrack.domain.model.organisation;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "DEPARTMENTS", uniqueConstraints = @UniqueConstraint(name = "UNQ_DEPT_NAME", columnNames = { "NAME" }))
public class Department extends AbstractIdentifiable<Department, Short> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 100)
	private String name;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "COMPANY_ID", nullable = false)
	private Company company;
}
