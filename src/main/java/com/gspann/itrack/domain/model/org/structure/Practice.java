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
 		@UniqueConstraint(name = UNQ_PRACTICE_NAME, columnNames = { "NAME"}),
 		@UniqueConstraint(name = UNQ_PRACTICE_DESCRIPTION, columnNames = { "DESCRIPTION"})
	}
)
// @formatter:on
@Immutable
public class Practice extends AbstractAssignable<String> {

	// MOBILE APPS, SST, DevOps
	@NotNull
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	@NotNull
	@Column(name = "DESCRIPTION", nullable = false, length = 100)
	private String description;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "LEAD_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PRACTICES_LEAD_RESOURCE_CODE))
	private Resource lead;

	public static PracticeCodeBuilder practice() {
		return new PracticeBuilder();
	}

	public interface PracticeCodeBuilder {
		PracticeNameBuilder codedAs(final String code);
	}

	public interface PracticeNameBuilder {
		PracticeDescriptionBuilder namedAs(final String name);
	}

	public interface PracticeDescriptionBuilder {
		PracticeLeadBuilder withDescription(final String description);
	}

	public interface PracticeLeadBuilder {
		Practice leadBy(final Resource lead);
	}

	private static class PracticeBuilder
			implements PracticeCodeBuilder, PracticeNameBuilder, PracticeDescriptionBuilder, PracticeLeadBuilder {
		private Practice practice;

		PracticeBuilder() {
			practice = new Practice();
		}

		@Override
		public PracticeNameBuilder codedAs(final String code) {
			practice.code = code;
			return this;
		}

		@Override
		public PracticeDescriptionBuilder namedAs(final String name) {
			practice.name = name;
			return this;
		}

		@Override
		public PracticeLeadBuilder withDescription(String description) {
			practice.description = description;
			return this;
		}

		@Override
		public Practice leadBy(Resource lead) {
			practice.lead = lead;
			return practice;
		}
	}
}
