package com.gspann.itrack.domain.model.business;

import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Account.class)
public abstract class Account_ extends com.gspann.itrack.domain.common.type.BaseAutoAssignableVersionableEntity_ {

	public static volatile SingularAttribute<Account, String> customerEntity;
	public static volatile SetAttribute<Account, Rebate> rebates;
	public static volatile SingularAttribute<Account, Resource> accountManager;
	public static volatile ListAttribute<Account, Project> projects;
	public static volatile SingularAttribute<Account, Boolean> customerTimeTracking;
	public static volatile SingularAttribute<Account, City> location;
	public static volatile SingularAttribute<Account, String> customerName;
	public static volatile SingularAttribute<Account, String> customerReportingManager;

}

