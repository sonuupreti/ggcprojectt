package com.gspann.itrack.domain.model.location;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor()
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = { "name", "country" }, callSuper = false)
@Entity
@Table(name = "STATES", uniqueConstraints = @UniqueConstraint(name = UNQ_STATE, columnNames = { "NAME",
		"COUNTRY_CODE" }))
@Immutable
public class State extends AbstractIdentifiable<Integer> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 20)
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "COUNTRY_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_STATES_COUNTRY_CODE))
	private Country country;

	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<City> cities = new HashSet<City>();

	public static State of(final String name, final Country country) {
		State state = new State();
		state.name = name;
		state.country = country;
		return state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("State {id=");
		builder.append(id());
		builder.append(", name=");
		builder.append(name);
		builder.append(", country=");
		builder.append(country);
		builder.append("}");
		return builder.toString();
	}
}
