package com.gspann.itrack.domain.model.org.structure;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.domain.model.common.type.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
// @formatter:off
@Table(name = "DESIGNATIONS", 
		uniqueConstraints = {
				@UniqueConstraint(name = UNQ_DESIG_NAME, columnNames = { "NAME", "DEPARTMENT_ID" }),
				@UniqueConstraint(name = UNQ_DESIG_LEVEL, columnNames = { "DEPARTMENT_ID", "LEVEL" })
		}
)
// @formatter:on

@Immutable
public class Designation extends AbstractIdentifiable<Short> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 100)
	private String name;

	@NotNull
	@Column(name = "LEVEL", nullable = false)
	private short level;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID", foreignKey = @ForeignKey(name = FK_DESIGNATIONS_DEPARTMENT_ID))
	private Department department;
}