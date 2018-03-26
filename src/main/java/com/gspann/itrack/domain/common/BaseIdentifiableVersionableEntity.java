package com.gspann.itrack.domain.common;

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
public class BaseIdentifiableVersionableEntity<Template extends Identifiable<IdType>, 
		IdType extends Number & Comparable<IdType> & Serializable, VersionType extends Comparable<VersionType> & Serializable>
		extends AbstractIdentifiable<Template, IdType> implements
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
