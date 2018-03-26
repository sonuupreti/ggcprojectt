package com.gspann.itrack.domain.model.organisation;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.AbstractIdentifiable;
import com.gspann.itrack.domain.common.location.City;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "COMPANIES", uniqueConstraints = @UniqueConstraint(name = "UNQ_COMP_NAME", columnNames = { "NAME" }))
public class Company extends AbstractIdentifiable<Company, Short> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 255)
	private String name;

	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(
	        name = "COMPANY_LOCATION",
	        joinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName="ID"),
	        inverseJoinColumns = @JoinColumn(name = "CITY_ID", referencedColumnName="ID")
	)
	private Set<City> locations = new HashSet<City>();
}
