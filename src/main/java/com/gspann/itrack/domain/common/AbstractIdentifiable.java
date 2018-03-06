package com.gspann.itrack.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import com.gspann.itrack.common.Constants;

import lombok.EqualsAndHashCode;

/**
 * 
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode
//@formatter:off
public abstract class AbstractIdentifiable<Template extends Identifiable<IdType>, 
		IdType extends Comparable<IdType> & Serializable>
        implements Identifiable<IdType> {
//@formatter:on

	@Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    @Column(name = "id", nullable = false)
    private IdType id;

    protected AbstractIdentifiable() {
    }

    @Override
    public IdType getId() {
        return id;
    }

}
