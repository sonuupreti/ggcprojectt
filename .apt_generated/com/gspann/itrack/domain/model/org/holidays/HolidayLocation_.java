package com.gspann.itrack.domain.model.org.holidays;

import com.gspann.itrack.domain.common.location.City;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HolidayLocation.class)
public abstract class HolidayLocation_ {

	public static volatile SingularAttribute<HolidayLocation, Occasion> occasion;
	public static volatile SingularAttribute<HolidayLocation, City> location;
	public static volatile SingularAttribute<HolidayLocation, HolidayLocationId> id;
	public static volatile SingularAttribute<HolidayLocation, Holiday> holiday;

}

