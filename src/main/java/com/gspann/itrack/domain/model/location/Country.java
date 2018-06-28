package com.gspann.itrack.domain.model.location;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.standard.CountryCode;
import com.gspann.itrack.domain.model.common.type.AbstractAssignable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
@Table(name = "COUNTRIES", uniqueConstraints = { @UniqueConstraint(name = UNQ_COUNTRY_NAME, columnNames = { "NAME" }) })
@Immutable
public class Country extends AbstractAssignable<String> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 100)
	private String name;

	@NotNull
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<State> states = new HashSet<State>();

	public static Country of(String alpha2, String name) {
		Country country = new Country();
		country.code = alpha2;
		country.name = name;
		return country;
	}

	public static Country ofUSA() {
		return of(CountryCode.US.alpha2(), CountryCode.US.countryName());
	}

	public static Country ofUK() {
		return of(CountryCode.UK.alpha2(), CountryCode.UK.countryName());
	}

	public static Country ofIndia() {
		return of(CountryCode.IN.alpha2(), CountryCode.IN.countryName());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Country [code=");
		builder.append(code());
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
