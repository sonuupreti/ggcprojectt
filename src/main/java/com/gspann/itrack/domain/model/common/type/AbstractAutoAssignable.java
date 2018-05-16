package com.gspann.itrack.domain.model.common.type;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.Hibernate.*;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@EqualsAndHashCode
//@formatter:off
public abstract class AbstractAutoAssignable<CodeType extends Serializable & Comparable<CodeType> & CharSequence>
        implements Assignable<CodeType> {
//@formatter:on

	@Id
	@GeneratedValue(generator = PREFIXED_SEQUENTIAL_CODE_GENERATOR)
	@Column(name = "CODE", nullable = false, length = 20)
	protected CodeType code;

	@Override
	public CodeType code() {
		return code;
	}
}
