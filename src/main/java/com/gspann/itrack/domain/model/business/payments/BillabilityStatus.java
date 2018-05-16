package com.gspann.itrack.domain.model.business.payments;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;
import com.gspann.itrack.domain.model.common.type.AbstractAssignable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
@Table(name = "BILLABILITY_STATUSES", uniqueConstraints = @UniqueConstraint(name = UNQ_BILLABILITY_STATUS_DESCRIPTION, columnNames = {
		"DESCRIPTION" }))
@Immutable
public class BillabilityStatus extends AbstractAssignable<String> {

	@NotNull
	@Column(name = "DESCRIPTION", nullable = false, length = 20)
	private String description;

	public static BillabilityStatus of(String code, String description) {
		BillabilityStatus billabilityStatus = new BillabilityStatus();
		billabilityStatus.code = code;
		billabilityStatus.description = description;
		return billabilityStatus;
	}

	public static BillabilityStatus byStatusCode(CODE statusCode) {
		return of(statusCode.name(), statusCode.description);
	}

	public static BillabilityStatus ofBillable() {
		return of(CODE.BILLABLE.name(), CODE.BILLABLE.value());
	}

	public static BillabilityStatus ofNonBillable() {
		return of(CODE.NON_BILLABLE.name(), CODE.NON_BILLABLE.value());
	}

	public static BillabilityStatus ofBuffer() {
		return of(CODE.BUFFER.name(), CODE.BUFFER.value());
	}

	public static enum CODE implements StringValuedEnum {

		// BILLABLE, NON_BILLABLE, BUFFER

		//@formatter:off
		BILLABLE("Billable"),
		NON_BILLABLE("Non Billable"),
		BUFFER("Buffer");
		//@formatter:on

		private final String description;

		private CODE(final String description) {
			this.description = description;
		}

		@Override
		public String value() {
			return this.description;
		}

		@Override
		public StringValuedEnum enumByValue(String description) {
			for (CODE e : values())
				if (e.value().equals(description))
					return e;
			throw new IllegalArgumentException();
		}
	}
}
