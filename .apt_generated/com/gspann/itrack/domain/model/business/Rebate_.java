package com.gspann.itrack.domain.model.business;

import java.time.Year;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rebate.class)
public abstract class Rebate_ extends com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity_ {

	public static volatile SingularAttribute<Rebate, Year> year;
	public static volatile SingularAttribute<Rebate, Float> rebate;
	public static volatile SingularAttribute<Rebate, Account> account;

}

