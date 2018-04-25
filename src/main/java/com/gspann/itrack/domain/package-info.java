//@formatter:off
@TypeDefs({
	@TypeDef(
		name = TYPE_DEF_JADIRA_MONEY, 
		defaultForType = Money.class, 
		typeClass = org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmountAndCurrency.class
	),
    @TypeDef(
    	name = TYPE_DEF_JADIRA_YEAR, 
        defaultForType = Year.class,
        typeClass = org.jadira.usertype.dateandtime.threeten.PersistentYearAsInteger.class
    ),
    @TypeDef(
    	name = TYPE_DEF_JADIRA_DURATION, 
        defaultForType = Duration.class,
        typeClass = org.jadira.usertype.dateandtime.threeten.PersistentDurationAsString.class
    ),
    @TypeDef(
    	name = TYPE_DEF_JADIRA_MONTH_OF_YEAR, 
        defaultForType = YearMonth.class,
        typeClass = org.jadira.usertype.dateandtime.threeten.PersistentYearMonthAsString.class
    )
})
//@formatter:on

package com.gspann.itrack.domain;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.Hibernate.*;

import java.time.Duration;
import java.time.Year;
import java.time.YearMonth;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.javamoney.moneta.Money;
