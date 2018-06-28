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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.domain.model.common.type.AbstractIdentifiable;
import com.gspann.itrack.domain.model.location.City;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
@Table(name = "COMPANIES", uniqueConstraints = @UniqueConstraint(name = UNQ_COMP_NAME, columnNames = { "NAME" }))
@Immutable
public class Company extends AbstractIdentifiable<Short> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 255)
	private String name;
	
	@NotNull
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Department> departments = new HashSet<Department>();
	
	@OneToMany(fetch = FetchType.EAGER)
	// @formatter:off
 	@JoinTable(
	        name = "COMPANY_LOCATION_MAP",
	        joinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName="ID", 
	        	foreignKey = @ForeignKey(name = FK_COMPANY_LOCATION_MAP_COMPANY_ID)),
	        inverseJoinColumns = @JoinColumn(name = "CITY_ID", referencedColumnName="ID", 
	        	foreignKey = @ForeignKey(name = FK_COMPANY_LOCATION_MAP_CITY_ID))
	)
	// @formatter:on
	private Set<City> locations = new HashSet<City>();
	
	public static Company of(final String name, final Set<City> locations) {
		Company company = new Company();
		company.name = name;
		company.locations = locations;
		return company;
	}
}
