package com.gspann.itrack.domain.model.location;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = { "name", "state" }, callSuper = false)
@Entity
@Table(name = "CITIES", uniqueConstraints = @UniqueConstraint(name = UNQ_CITY, columnNames = { "NAME", "STATE_ID" }))
@Immutable
public class City extends AbstractIdentifiable<Integer> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 20)
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "STATE_ID", nullable = false, foreignKey = @ForeignKey(name = FK_CITIES_STATE_ID))
	private State state;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("City [id=");
		builder.append(id());
		builder.append(", name=");
		builder.append(name);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}
}
