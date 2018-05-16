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
public abstract class AbstractIdentifiable<IdType extends Number & Comparable<IdType> & Serializable>
        implements Identifiable<IdType> {
//@formatter:on

	@Id
	@GeneratedValue(generator = iTrack_ID_GENERATOR)
	@Column(name = "ID", nullable = false)
	protected IdType id;

	@Override
	public IdType id() {
		return id;
	}
}
