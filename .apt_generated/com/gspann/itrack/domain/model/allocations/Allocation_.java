package com.gspann.itrack.domain.model.allocations;

import com.gspann.itrack.domain.model.business.payments.Bill;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Allocation.class)
public abstract class Allocation_ extends com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity_ {

	public static volatile SingularAttribute<Allocation, Short> proportion;
	public static volatile SingularAttribute<Allocation, Resource> resource;
	public static volatile ListAttribute<Allocation, Bill> billings;
	public static volatile SingularAttribute<Allocation, Project> project;

}

