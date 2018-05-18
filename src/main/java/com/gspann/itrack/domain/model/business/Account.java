package com.gspann.itrack.domain.model.business;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.type.BaseAutoAssignableVersionableEntity;
import com.gspann.itrack.domain.model.common.type.Buildable;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
// @formatter:off
@Table(name = "ACCOUNTS", 
 		uniqueConstraints = @UniqueConstraint(name = UNQ_CUSTOMER_NAME, columnNames = { "CUSTOMER_NAME" }),
 		indexes = { 
 				@Index(name = IDX_CUSTOMER_ENTITY, columnList = "CUSTOMER_ENTITY")  
 		}
)
// @formatter:on
public class Account extends BaseAutoAssignableVersionableEntity<String, Long> {

	@NotNull
	@Column(name = "CUSTOMER_NAME", nullable = false, length = 150)
	private String customerName; // Unique

	// @NotNull
	@Column(name = "CUSTOMER_ENTITY", nullable = true, length = 150)
	private String customerEntity; // customer entity is optional and can be same for multiple accounts

	@NotNull
	@Column(name = "CUSTOMER_REPORTING_MANAGER", nullable = false, length = 150)
	private String customerReportingManager;

	@Column(name = "CUSTOMER_TIME_TRACKING", length = 1)
	private boolean customerTimeTracking = false;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ACCOUNT_MANAGER_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_ACCOUNTS_MGR_RESOURCE_CODE))
	private Resource accountManager;

	// An account will have a single location,
	// in case the customer is at different locations then separate account will be
	// created for each location
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CITY_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_ACCOUNTS_CITY_ID))
	private City location;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ACCOUNT_CODE", nullable = false)
	@org.hibernate.annotations.OrderBy(clause = "YEAR desc")
	@Getter(value = AccessLevel.NONE)
	private Set<Rebate> rebates = new LinkedHashSet<Rebate>();

	public Set<Rebate> rebates() {
		return Collections.unmodifiableSet(this.rebates);
	}

	public Rebate rebateForYear(Year year) {
		// TODO: Implement later
		return null;
	}

	public void addRebate(Year year, float rebatePercent) {
		this.rebates.add(Rebate.of(rebatePercent).forYear(year));
	}

	@OneToMany(mappedBy = "account")
	@Getter(value = AccessLevel.NONE)
	private List<Project> projects = new ArrayList<Project>();

	public List<Project> projects() {
		return Collections.unmodifiableList(this.projects);
	}

	public void addProject(final Project project) {
		this.projects.add(project);
	}

	@OneToMany(mappedBy = "account")
	private List<SOW> sows = new ArrayList<SOW>();

	public static CustomerReportingManagerBuilder ofCustomer(final String name) {
		return new AccountBuilder(name);
	}

	public interface CustomerReportingManagerBuilder {
		public CustomerEntityBuilder reportingTo(final String customerManager);
	}

	public interface CustomerEntityBuilder {
		public CustomerTimeTrackingBuilder withEntity(final String entity);

		public CustomerTimeTrackingBuilder withNoEntity();
	}

	public interface CustomerTimeTrackingBuilder {
		public AccountManagerBuilder customerTimeTrackingAvailable();

		public AccountManagerBuilder customerTimeTrackingNotAvailable();
		public AccountManagerBuilder isCustomerTimeTrackingAvailable(final boolean trueOrFalse);
	}
	
	/*public interface CustomerTimeTrackingBuilder {
		public AccountManagerBuilder isCustomerTimeTrackingAvailable(final boolean trueOrFalse);
	}*/

	public interface AccountManagerBuilder {
		public AccountLocationBuilder managedBy(final Resource accountManager);
	}

	public interface AccountLocationBuilder {
		public AccountRebatePercentBuilder locatedAt(final City location);
	}

	public interface AccountRebatePercentBuilder extends Buildable<Account> {
		public AccountYearBuilder withRebateOf(final float rebate);
	}

	public interface AccountYearBuilder {
		public Buildable<Account> forYear(final Year year);
	}

	public static class AccountBuilder
			implements CustomerReportingManagerBuilder, CustomerEntityBuilder, CustomerTimeTrackingBuilder, AccountManagerBuilder,
			AccountLocationBuilder, AccountRebatePercentBuilder, AccountYearBuilder {
		private Account account;
		private float rebatePercent;

		public AccountBuilder(final String name) {
			this.account = new Account();
			this.account.customerName = name;
		}

		@Override
		public CustomerEntityBuilder reportingTo(String customerManager) {
			this.account.customerReportingManager = customerManager;
			return this;
		}

		@Override
		public CustomerTimeTrackingBuilder withEntity(String entity) {
			this.account.customerEntity = entity;
			return this;
		}

		@Override
		public CustomerTimeTrackingBuilder withNoEntity() {
			return this;
		}
		
		@Override
		public AccountManagerBuilder customerTimeTrackingAvailable() {
			this.account.customerTimeTracking = true;
			return this;
		}

		@Override
		public AccountManagerBuilder customerTimeTrackingNotAvailable() {
			this.account.customerTimeTracking = false;
			return this;
		}
		
		@Override
		public AccountManagerBuilder isCustomerTimeTrackingAvailable(final boolean trueOrFalse) {
			this.account.customerTimeTracking = trueOrFalse;
			return this;
		}

		@Override
		public AccountLocationBuilder managedBy(Resource accountManager) {
			this.account.accountManager = accountManager;
			return this;
		}

		@Override
		public AccountYearBuilder withRebateOf(float rebatePercent) {
			this.rebatePercent = rebatePercent;
			return this;
		}

		@Override
		public AccountRebatePercentBuilder locatedAt(City location) {
			this.account.location = location;
			return this;
		}

		@Override
		public Buildable<Account> forYear(Year year) {
			this.account.rebates.add(Rebate.of(this.rebatePercent).forYear(year));
			return this;
		}

		@Override
		public Account build() {
			return this.account;
		}
		
		
	}
	
	public void updateCustomerName(String customerName) {
		this.customerName=customerName;
	}
	
	public void updateCustomerEntity(String customerEntity) {
		this.customerEntity=customerEntity;
	}
	
	public void updatecustomerReportingManager(String customerReportingManager) {
		this.customerReportingManager=customerReportingManager;
	}
	
	public void updatecustomerTimeTracking(boolean customerTimeTracking) {
		this.customerTimeTracking=customerTimeTracking;
	}
	
	public void assignAccountManager(Resource accountManager) {
		
		this.accountManager=accountManager;
	}
	
	public void updateLocation(City location) {
		
		this.location=location;
	}
}
