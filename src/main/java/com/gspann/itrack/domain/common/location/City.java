package com.gspann.itrack.domain.common.location;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
@Entity
@Table(name = "LOCATION_CITY", uniqueConstraints = @UniqueConstraint(name = "UNQ_CITY", columnNames = { "NAME",
		"STATE_ID" }))
public class City extends AbstractIdentifiable<City, Integer> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 20)
	private String name;

	@NotNull
	@ManyToOne(targetEntity = com.gspann.itrack.domain.common.location.State.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
	@JoinColumn(name = "STATE_ID", nullable = false)
	private State state;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("City [id=");
		builder.append(id());
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
