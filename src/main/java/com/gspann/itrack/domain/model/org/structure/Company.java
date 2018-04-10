package com.gspann.itrack.domain.model.org.structure;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;
import java.util.HashSet;
import java.util.Set;

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

import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.common.type.AbstractAssignable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
@Table(name = "COMPANIES", uniqueConstraints = @UniqueConstraint(name = UNQ_COMP_NAME, columnNames = { "NAME" }))
@Immutable
public class Company extends AbstractAssignable<String> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 255)
	private String name;

	@OneToMany(fetch = FetchType.EAGER)
	// @formatter:off
 	@JoinTable(
	        name = "COMPANY_LOCATION_MAP",
	        joinColumns = @JoinColumn(name = "COMPANY_CODE", referencedColumnName="CODE", 
	        	foreignKey = @ForeignKey(name = FK_COMPANY_LOCATION_COMPANY_CODE)),
	        inverseJoinColumns = @JoinColumn(name = "CITY_ID", referencedColumnName="ID", 
	        	foreignKey = @ForeignKey(name = FK_COMPANY_LOCATION_CITY_ID))
	)
	// @formatter:on
	private Set<City> locations = new HashSet<City>();
	
	public static Company of(final String code, final String name, final Set<City> locations) {
		Company company = new Company();
		company.code = code;
		company.name = name;
		company.locations = locations;
		return company;
	}
}
