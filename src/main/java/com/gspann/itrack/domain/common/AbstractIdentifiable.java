package com.gspann.itrack.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import com.gspann.itrack.adapter.persistence.PersistenceConstants;

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
public abstract class AbstractIdentifiable<Template extends Identifiable<IdType>, 
		IdType extends Number & Comparable<IdType> & Serializable>
        implements Identifiable<IdType> {
//@formatter:on

	@Id
	@GeneratedValue(generator = PersistenceConstants.GLOBAL_SEQ_ID_GENERATOR)
    @Column(name = "ID", nullable = false)
    private IdType id;

    @Override
    public IdType id() {
        return id;
    }
}
