package com.gspann.itrack.domain.model.business.payments;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.javamoney.moneta.Money;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FTECost.class)
public abstract class FTECost_ extends com.gspann.itrack.domain.model.business.payments.NonFTECost_ {

	public static volatile SingularAttribute<FTECost, Money> annualSalary;
	public static volatile SingularAttribute<FTECost, Money> otherCost;
	public static volatile SingularAttribute<FTECost, Money> bonus;
	public static volatile SingularAttribute<FTECost, Money> commission;

}

