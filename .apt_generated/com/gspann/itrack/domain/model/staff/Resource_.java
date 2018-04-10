package com.gspann.itrack.domain.model.staff;

import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.business.payments.NonFTECost;
import com.gspann.itrack.domain.model.docs.Document;
import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.Department;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.org.structure.EngagementStatus;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Resource.class)
public abstract class Resource_ extends com.gspann.itrack.domain.common.type.BaseAutoAssignableVersionableEntity_ {

	public static volatile SetAttribute<Resource, Document> images;
	public static volatile SingularAttribute<Resource, String> loginId;
	public static volatile SingularAttribute<Resource, String> secondarySkills;
	public static volatile SingularAttribute<Resource, Gender> gender;
	public static volatile SingularAttribute<Resource, EngagementStatus> engagementStatus;
	public static volatile SingularAttribute<Resource, LocalDate> actualJoiningDate;
	public static volatile ListAttribute<Resource, NonFTECost> costings;
	public static volatile SingularAttribute<Resource, Boolean> customerTimeTracking;
	public static volatile SingularAttribute<Resource, String> emailId;
	public static volatile SingularAttribute<Resource, EmploymentStatus> employmentStatus;
	public static volatile SetAttribute<Resource, Document> resumes;
	public static volatile SetAttribute<Resource, Practice> practices;
	public static volatile SingularAttribute<Resource, LocalDate> expectedJoiningDate;
	public static volatile ListAttribute<Resource, Allocation> allocations;
	public static volatile ListAttribute<Resource, WeeklyTimeSheet> timesheets;
	public static volatile SingularAttribute<Resource, City> baseLocation;
	public static volatile SingularAttribute<Resource, String> name;
	public static volatile SingularAttribute<Resource, String> greytHRID;
	public static volatile SingularAttribute<Resource, Company> company;
	public static volatile SingularAttribute<Resource, String> primarySkills;
	public static volatile SingularAttribute<Resource, Designation> designation;
	public static volatile SingularAttribute<Resource, City> deputedLocation;
	public static volatile SingularAttribute<Resource, Department> department;
	public static volatile SingularAttribute<Resource, LocalDate> exitDate;

}

