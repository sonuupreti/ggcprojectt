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
@Table(name = "EMPLOYMENT_STATUSES", uniqueConstraints = @UniqueConstraint(name = UNQ_EMP_STATUS_DESCRIPTION, columnNames = {
		"DESCRIPTION" }))
@Immutable
public class EmploymentStatus extends AbstractAssignable<String> {

	// Active, Did Not Join, Terminated, On Long leave, On Bench, Pending, Resigned,
	// Absconding

	@NotNull
	@Column(name = "DESCRIPTION", nullable = false, length = 50)
	private String description;

	public boolean isOnboarded() {
		return code().equalsIgnoreCase(CODE.ACTIVE.name()) || code().equalsIgnoreCase(CODE.ON_LONG_LEAVE.name())
				|| code().equalsIgnoreCase(CODE.ON_BENCH.name()) || code().equalsIgnoreCase(CODE.RESIGNED.name());
	}

	public static EmploymentStatus of(String code, String description) {
		EmploymentStatus engagementStatus = new EmploymentStatus();
		engagementStatus.code = code;
		engagementStatus.description = description;
		return engagementStatus;
	}

	public static EmploymentStatus byStatusCode(CODE statusCode) {
		return of(statusCode.name(), statusCode.description);
	}

	public static EmploymentStatus pending() {
		return of(CODE.PENDING.name(), CODE.PENDING.description);
	}

	public static EmploymentStatus active() {
		return of(CODE.ACTIVE.name(), CODE.ACTIVE.description);
	}

	public static EmploymentStatus didNotJoin() {
		return of(CODE.DID_NOT_JOIN.name(), CODE.DID_NOT_JOIN.description);
	}

	public static EmploymentStatus terminated() {
		return of(CODE.TERMINATED.name(), CODE.TERMINATED.description);
	}

	public static EmploymentStatus onLongLeave() {
		return of(CODE.ON_LONG_LEAVE.name(), CODE.ON_LONG_LEAVE.description);
	}

	public static EmploymentStatus onBench() {
		return of(CODE.ON_BENCH.name(), CODE.ON_BENCH.description);
	}

	public static EmploymentStatus resigned() {
		return of(CODE.RESIGNED.name(), CODE.RESIGNED.description);
	}

	public static EmploymentStatus absconded() {
		return of(CODE.ABSCONDED.name(), CODE.ABSCONDED.description);
	}

	public enum CODE implements StringValuedEnum {

		// ACTIVE, DID_NOT_JOIN, TERMINATED, ON_LONG_LEAVE, ON_BENCH, PENDING, RESIGNED,
		// ABSCONDING

		//@formatter:off 
		PENDING("Joining Awaited"), 
		ACTIVE("Active On Project"), 
		DID_NOT_JOIN("Did Not Join"), 
		TERMINATED("Terminated"), 
		ON_LONG_LEAVE("On Long Leave"), 
		ON_BENCH("On Bench"),
		RESIGNED("Resigned"), 
		ABSCONDED("Absconded");
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
		public StringValuedEnum enumByValue(String statusCode) {
			for (CODE e : values())
				if (e.value().equals(statusCode))
					return e;
			throw new IllegalArgumentException();
		}
	}
}
