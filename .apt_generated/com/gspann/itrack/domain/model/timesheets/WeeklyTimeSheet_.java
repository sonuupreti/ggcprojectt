package com.gspann.itrack.domain.model.timesheets;

import com.gspann.itrack.domain.model.staff.Resource;
import java.time.Duration;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(WeeklyTimeSheet.class)
public abstract class WeeklyTimeSheet_ extends com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity_ {

	public static volatile SingularAttribute<WeeklyTimeSheet, Duration> totalHours;
	public static volatile SingularAttribute<WeeklyTimeSheet, Resource> resource;
	public static volatile SingularAttribute<WeeklyTimeSheet, Duration> standardHours;
	public static volatile SetAttribute<WeeklyTimeSheet, DailyTimeSheet> dailyTimeSheets;
	public static volatile SingularAttribute<WeeklyTimeSheet, byte[]> clientTimeSheetScreenShot;
	public static volatile SingularAttribute<WeeklyTimeSheet, Boolean> isMatchingCustomerTimeSheet;
	public static volatile SingularAttribute<WeeklyTimeSheet, ApprovalStatus> status;

}

