package com.gspann.itrack.domain.model.projects;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.business.SOW;
import com.gspann.itrack.domain.model.business.payments.Billing;
import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.Toggle;
import com.gspann.itrack.domain.model.common.type.BaseAutoAssignableVersionableEntity;
import com.gspann.itrack.domain.model.common.type.Buildable;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.skills.Technology;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
// @formatter:off
@Table(name = "PROJECTS", uniqueConstraints =  {
		@UniqueConstraint(name = UNQ_PROJECTS_NAME, columnNames = { "NAME" }),
		@UniqueConstraint(name = UNQ_PROJECTS_CUSTOMER_PROJECT_ID, columnNames = { "CUSTOMER_PROJECT_ID" }),
		@UniqueConstraint(name = UNQ_PROJECTS_CUSTOMER_PROJECT_NAME, columnNames = { "CUSTOMER_PROJECT_NAME" }),
	}
)
// @formatter:on
public class Project extends BaseAutoAssignableVersionableEntity<String, Long> {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_ACCOUNT_CODE))
	private Account account;

	@NotNull
	@Column(name = "NAME", nullable = false, length = 150)
	private String name;

	public void updateName(final String name) {
		this.name = name;
	}
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "PRJ_TYPE_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_PRJ_TYPE_CODE))
	private ProjectType type;

	public void updateProjectType(final ProjectType type) {
		this.type = type;
	}
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "PRJ_STATUS_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_PRJ_STATUS_CODE))
	private ProjectStatus status;

	public void updateProjectStatus(final ProjectStatus status) {
		this.status = status;
	}
	
	public void startOn(final LocalDate startDate) {
		this.dateRange = DateRange.dateRange().startingOn(startDate)
				.endingOn(this.dateRange != null ? dateRange.tillDate() : null);
		this.status = ProjectStatus.inProgress();
	}

	public void putOnHold() {
		this.status = ProjectStatus.onHold();
	}

	public void close() {
		this.dateRange.endOn(LocalDate.now());
		this.status = ProjectStatus.closed();
	}

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "LOCATION_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_LOC_ID))
	private City location;

	public void updateLocation(final City newLocation) {
		this.location = newLocation;
	}

	@NotNull
	private DateRange dateRange;

	public void updateStartDate(final LocalDate newStartDate) {
		dateRange.startOn(newStartDate);
	}

	public void updateEndDate(final LocalDate newEndDate) {
		dateRange.endOn(newEndDate);
	}

	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	// @formatter:off
 	@JoinTable(
	        name = "PROJECT_PRACTICE_MAP",
	        joinColumns = @JoinColumn(name = "PROJECT_CODE", referencedColumnName="CODE", 
	        foreignKey = @ForeignKey(name = FK_PROJECT_PRACTICE_MAP_PROJECT_CODE), unique = false),
	        inverseJoinColumns = @JoinColumn(name = "PRACTICE_CODE", referencedColumnName="CODE", 
	    	foreignKey = @ForeignKey(name = FK_PROJECT_PRACTICE_MAP_PRACTICE_CODE), unique = false)
	)
    // @formatter:on
	private Set<Practice> practices = new HashSet<Practice>();

	public void addPractice(final Practice practice) {
		this.practices.add(practice);
	}

	public void removePractice(final Practice practice) {
		this.practices.remove(practice);
	}

	public void updatePractices(final Set<Practice> practices) {
		this.practices = practices;
	}

	
	@NotNull
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	// @formatter:off
 	@JoinTable(
	        name = "PROJECT_TECHNOLOGY_MAP",
	        joinColumns = @JoinColumn(name = "PROJECT_CODE", referencedColumnName="CODE", 
	        foreignKey = @ForeignKey(name = FK_PROJECT_TECHNOLOGY_MAP_PROJECT_CODE), unique = false),
	        inverseJoinColumns = @JoinColumn(name = "TECHNOLOGY_ID", referencedColumnName="ID", 
	    	foreignKey = @ForeignKey(name = FK_PROJECT_TECHNOLOGY_MAP_TECHNOLOGY_ID), unique = false)
	)
    // @formatter:on
	private Set<Technology> technologies = new HashSet<Technology>();
	
	
	public void updateTechnologies(final Set<Technology> technologies) {
		this.technologies = technologies;
	}
////	@NotNull
////	@Column(name = "TECHNOLOGIES", nullable = false, length = 255)
////	private String technologies;
//
	
	
	
//	public void updateTechnology(final String technologies) {
//		this.technologies = technologies;
//	}

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "OFFSHORE_MGR_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_OFFSHORE_MGR_CODE))
	private Resource offshoreManager;

	public void assignOffshoreManager(final Resource offshoreManager) {
		this.offshoreManager = offshoreManager;
	}

	public void assignOnshoreManager(final Resource onshoreManager) {
		this.onshoreManager = onshoreManager;
	}

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ONSHORE_MGR_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_ONSHORE_MGR_CODE))
	private Resource onshoreManager;

	// Optional but unique
	@Column(name = "CUSTOMER_PROJECT_ID", nullable = true, length = 150)
	private String customerProjectId;

	public void updateCustomerProjectId(final String customerProjectId) {
		this.customerProjectId = customerProjectId;
	}

	// Optional but unique
	@Column(name = "CUSTOMER_PROJECT_NAME", nullable = true, length = 150)
	private String customerProjectName;

	public void updateCustomerProjectName(final String customerProjectName) {
		this.customerProjectName = customerProjectName;
	}

	@NotNull
	@Column(name = "CUSTOMER_MANAGER", nullable = false, length = 150)
	private String customerManager;

	public void updatecustomerManager(final String customerManager) {
		this.customerManager = customerManager;
	}

	public boolean isLeaveType() {
		return this.type.isLeaveType();
	}
	
	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@Getter(value = AccessLevel.NONE)
	private List<Allocation> allocations = new ArrayList<>();
	
	public List<Allocation> allocations() {
		return Collections.unmodifiableList(allocations);
	}
	
	public void allocate(final Set<Allocation> allocations) {
		this.allocations.addAll(allocations);
	}

	public ProjectAllocationProportionBuilder allocateWith(final Resource resource) {
		return new ProjectAllocationBuilder(resource);
	}

	public interface ProjectAllocationProportionBuilder {
		public ProjectAllocationClientTimeTrackingBuilder fully();

		public ProjectAllocationClientTimeTrackingBuilder partially(final short percentage);
	}

	public interface ProjectAllocationClientTimeTrackingBuilder {
		public ProjectAllocationStartDateBuilder onboardedToClientTimeTrackingSystem(Toggle yesOrNo);
	}

	public interface ProjectAllocationStartDateBuilder {
		public ProjectAllocationEndDateBuilder startingFrom(final LocalDate fromDate);

		public ProjectAllocationBillabilityStatusBuilder startingIndefinatelyFrom(final LocalDate fromDate);
	}

	public interface ProjectAllocationEndDateBuilder {
		public ProjectAllocationBillabilityStatusBuilder till(final LocalDate tillDate);
	}

	public interface ProjectAllocationBillabilityStatusBuilder {

		public Allocation asBuffer();

		public Allocation asNonBillable();

		public Allocation atHourlyRate(final Money money);

		public Allocation atBilling(final Billing billRate);
	}

	public class ProjectAllocationBuilder implements ProjectAllocationClientTimeTrackingBuilder, ProjectAllocationProportionBuilder,
			ProjectAllocationStartDateBuilder, ProjectAllocationEndDateBuilder, ProjectAllocationBillabilityStatusBuilder {
		private Resource resource;
		private Toggle clientTimeTracking;
		private short proportion;
		private LocalDate allocationStartDate;
		private LocalDate allocationEndDate;

		ProjectAllocationBuilder(final Resource resource) {
			this.resource = resource;
		}

		@Override
		public ProjectAllocationClientTimeTrackingBuilder fully() {
			this.proportion = 100;
			return this;
		}

		@Override
		public ProjectAllocationClientTimeTrackingBuilder partially(short percentage) {
			this.proportion = percentage;
			return this;
		}

		@Override
		public ProjectAllocationStartDateBuilder onboardedToClientTimeTrackingSystem(Toggle yesOrNo) {
			this.clientTimeTracking = yesOrNo;
			return this;
		}

		@Override
		public ProjectAllocationEndDateBuilder startingFrom(LocalDate fromDate) {
			this.allocationStartDate = fromDate;
			return this;
		}

		@Override
		public ProjectAllocationBillabilityStatusBuilder startingIndefinatelyFrom(LocalDate fromDate) {
			this.allocationStartDate = fromDate;
			return this;
		}

		@Override
		public ProjectAllocationBillabilityStatusBuilder till(LocalDate tillDate) {
			this.allocationEndDate = tillDate;
			return this;
		}
		
		@Override
		public Allocation asBuffer() {
			Allocation allocation = Allocation.partial(proportion).of(resource).in(Project.this)
					.startingFrom(allocationStartDate).till(allocationEndDate).asBuffer()
					.onboardedToClientTimeTrackingSystem(clientTimeTracking);
			allocations.add(allocation);
			return allocation;
		}

		@Override
		public Allocation asNonBillable() {
			Allocation allocation = Allocation.partial(proportion).of(resource).in(Project.this)
					.startingFrom(allocationStartDate).till(allocationEndDate).asNonBillable()
					.onboardedToClientTimeTrackingSystem(clientTimeTracking);
			allocations.add(allocation);
			return allocation;
		}

		@Override
		public Allocation atHourlyRate(final Money money) {
			Allocation allocation = Allocation.partial(proportion).of(resource).in(Project.this)
					.startingFrom(allocationStartDate).till(allocationEndDate).atHourlyRateOf(money)
					.onboardedToClientTimeTrackingSystem(clientTimeTracking);
			allocations.add(allocation);
			return allocation;
		}

		@Override
		public Allocation atBilling(Billing billRate) {
			Allocation allocation = Allocation.partial(proportion).of(resource).in(Project.this)
					.startingFrom(allocationStartDate).till(allocationEndDate).withBilling(billRate)
					.onboardedToClientTimeTrackingSystem(clientTimeTracking);
			allocations.add(allocation);
			return allocation;
		}
	}

	@ManyToMany(mappedBy = "projects")
	private Set<SOW> sows = new HashSet<SOW>();

	public void addSOW(final SOW sow) {
		this.sows.add(sow);
	}

	public static ProjectTypeBuilder project() {
		return new ProjectBuilder();
	}

	public interface ProjectTypeBuilder {
		ProjectStatusBuilder fixBid();

		ProjectStatusBuilder TnM();

		ProjectStatusBuilder milestone();

		ProjectStatusBuilder investment();

		ProjectStatusBuilder bench();

		ProjectStatusBuilder paidLeave();

		ProjectStatusBuilder unpaidLeave();
		
		ProjectStatusBuilder withType(final ProjectType type);
	}

	public interface ProjectStatusBuilder {
		NameBuilder asPending();

		NameBuilder asInProgress();

		NameBuilder asOnHold();

		NameBuilder asClosed();

		NameBuilder withStatus(final ProjectStatus status);
	}

	public interface NameBuilder {
		LocationBuilder namedAs(final String name);
	}

	public interface LocationBuilder {
		AccountBuilder locatedAt(final City location);
	}

	public interface AccountBuilder {
		StartDateBuilder inAccount(final Account Account);
	}

	public interface StartDateBuilder {
		EndDateBuilder startingFrom(final LocalDate startDate);

		ProjectPracticeBuilder startingIndefinatelyFrom(final LocalDate startDate);

		TechnologiesBuilder withPractices(Set<Practice> practices);
	}

	public interface EndDateBuilder {
		ProjectPracticeBuilder till(final LocalDate endDate);
	}

	public interface ProjectPracticeBuilder {
		TechnologiesBuilder withPractices(final Set<Practice> practices);
	}

	public interface TechnologiesBuilder {
		OffshoreManagerBuilder withTechnologies(final Set<Technology> technologies);
	}

	public interface OffshoreManagerBuilder {
		OnshoreManagerBuilder atOffshoreManagedBy(final Resource offshoreManager);
	}

	public interface OnshoreManagerBuilder {
		CustomerManagerBuilder atOnshoreManagedBy(final Resource onshoreManager);
	}

	public interface CustomerManagerBuilder {
		CustomerProjectIdBuilder atCustomerEndManagedBy(final String customerManager);
	}

	public interface CustomerProjectIdBuilder extends Buildable<Project> {
		CustomerProjectNameBuilder withCustomerProjectId(final String customerProjectId);
	}
	
	public interface CustomerProjectNameBuilder extends Buildable<Project> {
		Buildable<Project> withCustomerProjectName(final String customerProjectName);
	}

	public static class ProjectBuilder
			implements ProjectTypeBuilder, ProjectStatusBuilder, NameBuilder, LocationBuilder, AccountBuilder,
			StartDateBuilder, EndDateBuilder, ProjectPracticeBuilder, TechnologiesBuilder, OffshoreManagerBuilder,
			OnshoreManagerBuilder, CustomerManagerBuilder, CustomerProjectIdBuilder, CustomerProjectNameBuilder {

		private Project project = new Project();
		private LocalDate projectStartDate;

		ProjectBuilder() {
		}

		@Override
		public ProjectStatusBuilder fixBid() {
			this.project.type = ProjectType.fixBid();
			return this;
		}

		@Override
		public ProjectStatusBuilder TnM() {
			this.project.type = ProjectType.TnM();
			return this;
		}

		@Override
		public ProjectStatusBuilder milestone() {
			this.project.type = ProjectType.milestone();
			return this;
		}

		@Override
		public ProjectStatusBuilder investment() {
			this.project.type = ProjectType.investment();
			return this;
		}

		@Override
		public ProjectStatusBuilder bench() {
			this.project.type = ProjectType.bench();
			return this;
		}

		@Override
		public ProjectStatusBuilder paidLeave() {
			this.project.type = ProjectType.paidLeave();
			return this;
		}

		@Override
		public ProjectStatusBuilder unpaidLeave() {
			this.project.type = ProjectType.unpaidLeave();
			return this;
		}

		@Override
		public ProjectStatusBuilder withType(ProjectType type) {
			this.project.type = type;
			return this;
		}

		public NameBuilder asPending() {
			this.project.status = ProjectStatus.pending();
			return this;
		}

		public NameBuilder asInProgress() {
			this.project.status = ProjectStatus.inProgress();
			return this;
		}

		public NameBuilder asOnHold() {
			this.project.status = ProjectStatus.onHold();
			return this;
		}

		public NameBuilder asClosed() {
			this.project.status = ProjectStatus.closed();
			return this;
		}
		
		@Override
		public NameBuilder withStatus(ProjectStatus status) {
			this.project.status = status;
			return this;
		}

		@Override
		public LocationBuilder namedAs(final String name) {
			this.project.name = name;
			return this;
		}

		@Override
		public AccountBuilder locatedAt(City location) {
			this.project.location = location;
			return this;
		}

		@Override
		public StartDateBuilder inAccount(final Account account) {
			this.project.account = account;
			return this;
		}

		@Override
		public EndDateBuilder startingFrom(final LocalDate startDate) {
			this.projectStartDate = startDate;
			return this;
		}

		@Override
		public ProjectPracticeBuilder till(final LocalDate endDate) {
			this.project.dateRange = DateRange.dateRange().startingOn(this.projectStartDate).endingOn(endDate);
			return this;
		}

		@Override
		public ProjectPracticeBuilder startingIndefinatelyFrom(LocalDate startDate) {
			this.project.dateRange = DateRange.dateRange().startIndefinitelyOn(startDate);
			return this;
		}

		@Override
		public TechnologiesBuilder withPractices(Set<Practice> practices) {
			this.project.practices = practices;
			return this;
		}

		@Override
		public OffshoreManagerBuilder withTechnologies(Set<Technology> technologies) {
			this.project.technologies = technologies;
			return this;
		}

		@Override
		public OnshoreManagerBuilder atOffshoreManagedBy(Resource offshoreManager) {
			this.project.offshoreManager = offshoreManager;
			return this;
		}

		@Override
		public CustomerManagerBuilder atOnshoreManagedBy(Resource onshoreManager) {
			this.project.onshoreManager = onshoreManager;
			return this;
		}

		@Override
		public CustomerProjectIdBuilder atCustomerEndManagedBy(final String customerManager) {
			this.project.customerManager = customerManager;
			return this;
		}

		@Override
		public CustomerProjectNameBuilder withCustomerProjectId(String customerProjectId) {
			this.project.customerProjectId = customerProjectId;
			return this;
		}

		@Override
		public Buildable<Project> withCustomerProjectName(String customerProjectName) {
			this.project.customerProjectName = customerProjectName;
			return this;
		}

		@Override
		public Project build() {
			return this.project;
		}
	}
}
