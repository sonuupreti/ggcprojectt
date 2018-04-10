package com.gspann.itrack.domain.common.location;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.domain.common.type.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor()
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "STATES", uniqueConstraints = @UniqueConstraint(name = UNQ_STATE, columnNames = { "NAME",
		"COUNTRY_CODE" }))
@Immutable
public class State extends AbstractIdentifiable<Integer> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 20)
	private String name;

	@NotNull
	@ManyToOne(targetEntity = com.gspann.itrack.domain.common.location.Country.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "COUNTRY_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_STATES_COUNTRY_CODE))
	private Country country;

	// @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy =
	// "state", fetch=FetchType.LAZY)
	// private Set<City> cities = new HashSet<City>();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("State {id=");
		builder.append(id());
		builder.append(", name=");
		builder.append(name);
		builder.append("}");
		return builder.toString();
	}
}
