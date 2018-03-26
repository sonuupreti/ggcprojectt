package com.gspann.itrack.domain.model.business;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.common.BaseAssignableVersionableEntity;
import com.gspann.itrack.domain.common.location.City;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "ACCOUNTS", uniqueConstraints = @UniqueConstraint(name = "UNQ_CUSTOMER_NAME", columnNames = { "CUSTOMER_NAME" }))
public class Account extends BaseAssignableVersionableEntity<Account, String, Long> {

	@NotNull
	@Column(name = "CUSTOMER_NAME", nullable = false, length = 100)
	private String customerName;
	
//	@NotNull
	@Column(name = "CUSTOMER_ENTITY", nullable = true, length = 150)
	private String customerEntity; //customer entity is optional and can be same for multiple accounts

	@NotNull
	@Column(name = "CUSTOMER_REPORTING_MANAGER", nullable = false, length = 150)
	private String customerReportingManager;

//	@NotNull
//	@OneToOne(fetch = FetchType.EAGER, optional = false)
//	@JoinColumn(unique = true, name = "ACCOUNT_MANAGER_ID")
//	private Resource accountManager;

	@Column(name = "IS_CUSTOMER_TIME_TRACKING_SYSTEM_AVAILABLE", length = 1)
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean isCustomerTimeTrackingSystemAvailable = false;

//	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
//	@JoinColumn(unique = true, name = "REBATE_ID")
//	private Rebate rebate;

	// An account will have a single location, 
	// in case the customer is at different locations then separate account will be created for each location
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "LOCATION_ID", nullable = false)
	private City location;
	
//	@Column(name = "BILL_RATE")
	@Columns(columns = { @Column(name = "BILLING_CURRENCY"), @Column(name = "BILLING_AMOUNT") })
	@Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmountAndCurrency")
	private Money billRate;
}
