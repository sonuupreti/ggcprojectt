package com.gspann.itrack.domain.model.org.holidays;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Holiday.class)
public abstract class Holiday_ extends com.gspann.itrack.domain.common.type.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Holiday, LocalDate> date;
	public static volatile SingularAttribute<Holiday, HolidayCalendar> calendar;
	public static volatile ListAttribute<Holiday, HolidayLocation> locationOcassions;

}

