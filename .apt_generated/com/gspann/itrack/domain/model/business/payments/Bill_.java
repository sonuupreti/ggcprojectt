package com.gspann.itrack.domain.model.business.payments;

import com.gspann.itrack.domain.model.allocations.Allocation;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bill.class)
public abstract class Bill_ extends com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity_ {

	public static volatile SingularAttribute<Bill, Allocation> allocation;
	public static volatile SingularAttribute<Bill, BillabilityStatus> billabilityStatus;

}

