package com.gspann.itrack.domain.model.timesheets;

import java.time.DayOfWeek;
import java.time.Duration;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DailyTimeSheet.class)
public abstract class DailyTimeSheet_ extends com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity_ {

	public static volatile SetAttribute<DailyTimeSheet, TimeSheetEntry> entries;
	public static volatile SingularAttribute<DailyTimeSheet, WeeklyTimeSheet> weeklyTimeSheet;
	public static volatile SingularAttribute<DailyTimeSheet, Duration> totalHours;
	public static volatile SingularAttribute<DailyTimeSheet, Duration> standardHours;
	public static volatile SingularAttribute<DailyTimeSheet, DayOfWeek> dow;

}

