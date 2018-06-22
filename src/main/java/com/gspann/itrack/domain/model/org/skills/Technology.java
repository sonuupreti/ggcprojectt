package com.gspann.itrack.domain.model.org.skills;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.type.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.var;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode(of = "name", callSuper = false)
@Entity
@Table(name = "TECHNOLOGIES", uniqueConstraints = @UniqueConstraint(name = UNQ_TECHNOLOGY_NAME, columnNames = {
		"NAME" }))
public class Technology extends AbstractIdentifiable<Integer> {

	@NotNull
	@Column(name = "NAME", nullable = false, length = 100)
	private String name;
	
	public static Set<Technology> setOf(String ... technologyNames) {
		Set<Technology> technologies = new HashSet<>();
		for(var technology: technologyNames) {
			technologies.add(Technology.of(technology));
		}
		return technologies;
	}
}
