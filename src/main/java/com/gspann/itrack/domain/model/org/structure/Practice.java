package com.gspann.itrack.domain.model.org.structure;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.domain.common.type.AbstractAssignable;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@ToString
@Entity
// @formatter:off
@Table(name = "PRACTICES", uniqueConstraints = {
 		@UniqueConstraint(name = UNQ_PRACTICE_NAME, columnNames = { "NAME"})
	}
)
// @formatter:on
@Immutable
public class Practice extends AbstractAssignable<String> {

	// MOBILE APPS, SST, DevOps
	@NotNull
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "LEAD_CODE", unique = false, nullable = true, foreignKey = @ForeignKey(name = FK_PRACTICES_LEAD_RESOURCE_CODE))
	private Resource lead;

	public static Practice of(final String code, final String name) {
		Practice practice = new Practice();
		practice.code = code;
		practice.name = name;
		return practice;
	}

	public static Practice of(final String code, final String name, final Resource lead) {
		Practice practice = new Practice();
		practice.code = code;
		practice.name = name;
		practice.lead = lead;
		return practice;
	}
	
	public void updateLead(final Resource lead) {
		this.lead = lead;
	}
}
