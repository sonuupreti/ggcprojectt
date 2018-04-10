package com.gspann.itrack.domain.model.timesheets;

import com.gspann.itrack.domain.model.projects.Project;
import java.time.Duration;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TimeSheetEntry.class)
public abstract class TimeSheetEntry_ extends com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity_ {

	public static volatile SingularAttribute<TimeSheetEntry, DailyTimeSheet> dailyTimeSheet;
	public static volatile SingularAttribute<TimeSheetEntry, Duration> hours;
	public static volatile SingularAttribute<TimeSheetEntry, Project> project;
	public static volatile SingularAttribute<TimeSheetEntry, String> description;
	public static volatile SingularAttribute<TimeSheetEntry, Boolean> isApproved;

}

