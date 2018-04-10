package com.gspann.itrack.domain.model.org.holidays;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;
import com.gspann.itrack.domain.common.type.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "OCCASIONS", uniqueConstraints = @UniqueConstraint(name = UNQ_OCCASION_NAME, columnNames = { "NAME" }))
@Immutable
public class Occasion extends AbstractIdentifiable<Short> {
	// Holi, Diwali etc.

	@NotNull
	@Column(name = "NAME", nullable = false, unique = true, length = 100)
	private String name;

	public static enum REASON implements StringValuedEnum {

		//@formatter:off
		NEW_YEAR("New Year"),
		// India
		EVE_HOLI("Holi"),
		EVE_DIWALI("Diwali"),
		
		// US/UK
		EVE_CHRISTMAS("Christmas"),
		EVE_EASTER("Easter"),
		GOOD_FRIDAY("Good Friday");
		//@formatter:on

		private final String description;

		private REASON(final String description) {
			this.description = description;
		}

		@Override
		public String value() {
			return this.description;
		}

		@Override
		public StringValuedEnum enumByValue(String description) {
			for (REASON e : values())
				if (e.value().equals(description))
					return e;
			throw new IllegalArgumentException();
		}
	}
}
