package com.gspann.itrack.domain.model.org.structure;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "DEPARTMENTS", uniqueConstraints = @UniqueConstraint(name = UNQ_DEPT_NAME, columnNames = { "NAME", "COMPANY_ID" }))
@Immutable
public class Department extends AbstractIdentifiable<Short> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 100)
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "COMPANY_ID", foreignKey = @ForeignKey(name = FK_DEPARTMENTS_COMPANY_ID))
	private Company company;
	
	@NotNull
	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Designation> designations = new HashSet<Designation>();
}
