package com.gspann.itrack.domain.model.common.type;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.NoArgsConstructor;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
//@formatter:off
public class BaseAssignableVersionableEntity<CodeType extends Serializable & Comparable<CodeType> & CharSequence, 
		VersionType extends Comparable<VersionType> & Serializable>
		extends AbstractAssignable<CodeType> implements
		Versionable<VersionType> {
//@formatter:on

	@Version
	@Access(AccessType.FIELD)
	@Column(name = "version", nullable = false)
	private VersionType version;

	@Override
	public VersionType version() {
		return version;
	}
}
