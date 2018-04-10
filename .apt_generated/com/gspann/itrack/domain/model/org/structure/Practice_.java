package com.gspann.itrack.domain.model.org.structure;

import com.gspann.itrack.domain.model.staff.Resource;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Practice.class)
public abstract class Practice_ extends com.gspann.itrack.domain.common.type.AbstractAssignable_ {

	public static volatile SingularAttribute<Practice, String> name;
	public static volatile SingularAttribute<Practice, String> description;
	public static volatile SingularAttribute<Practice, Resource> lead;

}

