package com.gspann.itrack.domain.model.org.holidays;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;
import com.gspann.itrack.domain.model.common.type.AbstractIdentifiable;

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

	@NotNull
	@Column(name = "NAME", nullable = false, unique = true, length = 100)
	private String name;

	public static Occasion byReasonCode(REASON reason) {
		return of(reason.value());
	}

	public static enum REASON implements StringValuedEnum {

		//@formatter:off
		GLOBAL_DAY_NEW_YEAR("New Year"),
		GLOBAL_EVE_CHRISTMAS("Christmas Day"),
		// USA
		US_NH_MEMORIAL_DAY("Memorial Day"),
		US_NH_INDEPENDENCE_DAY("USA Independence Day"),
		US_NH_LABOR_DAY("Labor Day"),
		EVE_THANKS_GIVING_DAY_1("Thanksgiving Day - 1"),
		EVE_THANKS_GIVING_DAY_2("Thanksgiving Day - 2"),
		
		// India
		IN_NH_REPUBLIC_DAY("Republic Day"),
		IN_NH_INDEPENDENCE_DAY("Independence Day"),
		IN_NH_GANDHI_JAYANTI("Gandhi Jayanti"),
		EVE_PONGAL("Pongal"),
		EVE_HOLI("Holi"),
		EVE_DIWALI("Diwali"),
		EVE_GANESH_CHATURTHI("Ganesh Chaturthi"),
		EVE_ID_UL_FITR_RAMZAN("Id-ul-Fitr / Ramzan"),
		
		// UK
		UK_NH_BOXING_DAY("Boxing Day"),
		EVE_EASTER_MONDAY("Easter Monday"),
		EVE_GOOD_FRIDAY("Good Friday");
		//@formatter:on

		private final String value;

		private REASON(final String value) {
			this.value = value;
		}

		@Override
		public String value() {
			return this.value;
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
