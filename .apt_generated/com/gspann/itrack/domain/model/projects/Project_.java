package com.gspann.itrack.domain.model.projects;

import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.staff.Resource;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ extends com.gspann.itrack.domain.common.type.BaseAutoAssignableVersionableEntity_ {

	public static volatile SingularAttribute<Project, String> technologies;
	public static volatile SingularAttribute<Project, String> customerManager;
	public static volatile SingularAttribute<Project, String> name;
	public static volatile SingularAttribute<Project, ProjectType> projectType;
	public static volatile SingularAttribute<Project, Resource> onshoreManager;
	public static volatile SingularAttribute<Project, Resource> offshoreManager;
	public static volatile SingularAttribute<Project, City> location;
	public static volatile SingularAttribute<Project, String> customerProjectName;
	public static volatile SingularAttribute<Project, Account> account;
	public static volatile SetAttribute<Project, Practice> practices;
	public static volatile SingularAttribute<Project, String> customerProjectId;

}

