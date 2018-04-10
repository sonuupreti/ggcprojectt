package com.gspann.itrack.domain.model.org.structure;

import com.gspann.itrack.domain.common.location.City;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ extends com.gspann.itrack.domain.common.type.AbstractAssignable_ {

	public static volatile SingularAttribute<Company, String> name;
	public static volatile SetAttribute<Company, City> locations;

}

