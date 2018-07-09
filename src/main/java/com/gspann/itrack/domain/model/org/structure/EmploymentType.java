package com.gspann.itrack.domain.model.org.structure;

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
@Table(name = "EMPLOYMENT_TYPES", uniqueConstraints = @UniqueConstraint(name = UNQ_EMP_TYPE_DESCRIPTION, columnNames = {
		"DESCRIPTION" }))
@Immutable
public class EmploymentType extends AbstractAssignable<String> {

	@NotNull
	@Column(name = "DESCRIPTION", nullable = false, length = 50)
	private String description;

	public static EmploymentType of(String code, String description) {
		EmploymentType employmentStatus = new EmploymentType();
		employmentStatus.code = code;
		employmentStatus.description = description;
		return employmentStatus;
	}

	public static EmploymentType byStatusCode(CODE statusCode) {
		return of(statusCode.code(), statusCode.description);
	}

	public static EmploymentType fullTimeEmployee() {
		return of(CODE.FULLTIME_EMPLOYEE.code, CODE.FULLTIME_EMPLOYEE.description);
	}

	public static EmploymentType directContractor() {
		return of(CODE.DIRECT_CONTRACTOR.code, CODE.DIRECT_CONTRACTOR.description);
	}

	public static EmploymentType subContractor() {
		return of(CODE.SUB_CONTRACTOR.code, CODE.SUB_CONTRACTOR.description);
	}

	public static EmploymentType w2Consultant() {
		return of(CODE.W2_CONSULTANT.code, CODE.W2_CONSULTANT.description);
	}
	
	public static boolean isFTE(String code) {
	    return (CODE.FULLTIME_EMPLOYEE.code().equalsIgnoreCase(code) || CODE.W2_CONSULTANT.code().equalsIgnoreCase(code));
	}

	public boolean isFTE() {
	    return (CODE.FULLTIME_EMPLOYEE.code().equalsIgnoreCase(this.code) || CODE.W2_CONSULTANT.code().equalsIgnoreCase(this.code));
	}

	public enum CODE implements StringValuedEnum {
		// FULLTIME_EMPLOYEE("FTE"), DIRECT_CONTRACTOR("DC"), SUB_CONTRACTOR("SC"),
		// W2("W2");

		//@formatter:off
		FULLTIME_EMPLOYEE("FTE", "Full-time Employee"),
		DIRECT_CONTRACTOR("DC", "Direct Contractor"),
		SUB_CONTRACTOR("SC", "Sub Contractor"),
		W2_CONSULTANT("W2", "W2 Consultant");
		//@formatter:on

		private final String code;

		private final String description;

		private CODE(final String code, final String description) {
			this.code = code;
			this.description = description;
		}

		public String code() {
			return this.code;
		}

		@Override
		public String value() {
			return this.description;
		}

		@Override
		public CODE enumByValue(String description) {
			for (CODE e : values())
				if (e.value().equals(description))
					return e;
			throw new IllegalArgumentException();
		}
	}
	
	public static EmploymentType getEmploymentType(String code) {
		for (CODE e : CODE.values())
			if (e.code().equals(code))
				return EmploymentType.of(e.code, e.description);
		throw new IllegalArgumentException();
	}
}
