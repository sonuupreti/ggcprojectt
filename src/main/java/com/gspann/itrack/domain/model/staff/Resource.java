package com.gspann.itrack.domain.model.staff;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;
import static com.gspann.itrack.common.constants.ValidationConstant.Resource.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.NaturalId;
import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.common.DateRange;
import com.gspann.itrack.domain.common.Toggle;
import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.common.type.BaseAutoAssignableVersionableEntity;
import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.business.SOW;
import com.gspann.itrack.domain.model.business.payments.Bill;
import com.gspann.itrack.domain.model.business.payments.Billing;
import com.gspann.itrack.domain.model.business.payments.Costing;
import com.gspann.itrack.domain.model.docs.Document;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;

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
@Table(name = "RESOURCES", 
		uniqueConstraints = {
				@UniqueConstraint(name = UNQ_RESOURCES_LOGIN_ID, columnNames = {"LOGIN_ID"}),
				@UniqueConstraint(name = UNQ_RESOURCES_EMAIL_ID, columnNames = {"EMAIL_ID"}),
				@UniqueConstraint(name = UNQ_RESOURCES_GREYT_HR_ID, columnNames = {"GREYT_HR_ID"})
		},
		indexes = { 
				@Index(name = IDX_RESOURCES_NAME, columnList = "NAME")    
		}
 )
// @formatter:on
public class Resource extends BaseAutoAssignableVersionableEntity<String, Long> {

	@Pattern(regexp = REGEX_LOGIN_ID, message = MESSAGE_LOGIN_ID_INVALID)
	@NotNull(message = MESSAGE_LOGIN_ID_MANDATORY)
	@NaturalId(mutable = false)
	@Column(name = "LOGIN_ID", nullable = false, length = 100)
	private String loginId;

	@Email(message = MESSAGE_EMAIL_INVALID)
	@NaturalId(mutable = false)
	@Column(name = "EMAIL_ID", nullable = false, length = 255)
	private String emailId;

	@Pattern(regexp = REGEX_GREYT_HR_ID, message = MESSAGE_GREYT_HR_ID_INVALID)
	@Column(name = "GREYT_HR_ID", nullable = true, length = 100)
	private String greytHRID;

	@NotNull
	@Column(name = "NAME", nullable = false, length = 255)
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", nullable = false, length = 10)
	private Gender gender;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "DESIGNATION_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_DESIGNATION_ID))
	private Designation designation;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "EMP_TYPE_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_EMP_TYPE_CODE))
	private EmploymentType employmentType;

	public boolean isFTE() {
		return this.employmentType == EmploymentType.fullTimeEmployee();
	}

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "EMP_STATUS_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_EMP_STATUS_CODE))
	private EmploymentStatus employmentStatus;

	public void onboard(final LocalDate joiningDate, final Project project) {
		this.actualJoiningDate = joiningDate;
		this.employmentStatus = EmploymentStatus.active();
		// TODO: Need to verify if need to put on bench, if no other project allocation
		// is required or not?
		// TODO: Allocate to supplied project
		implicitlyAllocateToTimeOffProjects(joiningDate);
	}

	public void onBoardToBench(final LocalDate joiningDate) {
		this.actualJoiningDate = joiningDate;
		this.employmentStatus = EmploymentStatus.active();
		// TODO: Allocate to bench project
		implicitlyAllocateToTimeOffProjects(joiningDate);
	}

	private void implicitlyAllocateToTimeOffProjects(final LocalDate joiningDate) {
		// TODO: Allocate to paid leave and unpaid leave, projects, with 0% proportion,
		// These two projects are of Time-Off type and needs to be handled slight
		// differently while calculating profit and loss
	}

	public void markDidNotJoin() {
		this.employmentStatus = EmploymentStatus.didNotJoin();
	}

	public void terminate(final LocalDate exitDate) {
		this.employmentStatus = EmploymentStatus.terminated();
		this.exitDate = exitDate;
		// Update allocation with end date
	}

	public void putOnLongLeave(final LocalDate fromDate) {
		this.employmentStatus = EmploymentStatus.onLongLeave();
	}

	public void joinAfterLongLeave(final LocalDate fromDate) {
		this.employmentStatus = EmploymentStatus.active();
	}

	public void putOnBench(final LocalDate fromDate) {
		this.employmentStatus = EmploymentStatus.onBench();
		// Add allocation to bench project, ending the allocation to previous project
	}

	public void markResigned() {
		this.employmentStatus = EmploymentStatus.resigned();
	}

	public void markAbsconded(final LocalDate fromDate) {
		this.employmentStatus = EmploymentStatus.absconded();
		// Update allocation with end date
	}

	// The base location may not change as of now, but in future it may change
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "BASE_LOC_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_BASE_LOC_ID))
	private City baseLocation;

	// The deputed location will be the location where the resource is working.
	// It may or may not be same as project location.
	// In case of short term relocation the deputed location will be changed.
	// In case of long term relocation the resource will be terminated from original
	// company and will join as a new resource at new location and hence company.
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "DEPUTED_LOC_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_DEPUTED_LOC_ID))
	private City deputedLocation;

	public void relocate(final City newLocation) {
		this.deputedLocation = newLocation;
	}

	@NotEmpty(message = MESSAGE_PRIMARY_SKILLS_MANDATORY)
	@Column(name = "PRIMARY_SKILLS", nullable = false, length = 255)
	private String primarySkills;

	public void updatePrimarySkills(final String newPrimarySkills) {
		this.primarySkills = newPrimarySkills;
	}

	@NotEmpty(message = MESSAGE_SECONDARY_SKILLS_MANDATORY)
	@Column(name = "SECONDARY_SKILLS", nullable = false, length = 255)
	private String secondarySkills;

	public void updateSecondarySkills(final String newSecondarySkills) {
		this.primarySkills = newSecondarySkills;
	}

	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	// @formatter:off
 	@JoinTable(name = "RESOURCE_PRACTICE_MAP", 
 		joinColumns = @JoinColumn(name = "RESOURCE_CODE", referencedColumnName = "CODE", 
 		foreignKey = @ForeignKey(name = FK_RESOURCE_PRACTICE_MAP_RESOURCE_CODE), unique = false), 
		inverseJoinColumns = @JoinColumn(name = "PRACTICE_CODE", referencedColumnName = "CODE", 
		foreignKey = @ForeignKey(name = FK_RESOURCE_PRACTICE_MAP_PRACTICE_CODE), unique = false))
	// @formatter:on
	@Getter(value = AccessLevel.NONE)
	private Set<Practice> practices = new HashSet<Practice>();

	public Set<Practice> practices() {
		return Collections.unmodifiableSet(this.practices);
	}

	public void updatePractices(final Set<Practice> newPractices) {
		this.practices = newPractices;
	}

	public void addPractice(final Practice newPractice) {
		this.practices.add(newPractice);
	}

	public void removePractice(final Practice practice) {
		this.practices.remove(practice);
	}

	@Column(name = "EXPECTED_JOINING_DATE", nullable = true)
	private LocalDate expectedJoiningDate;

	@Column(name = "ACTUAL_JOINING_DATE", nullable = true)
	private LocalDate actualJoiningDate;

	@Column(name = "EXIT_DATE", nullable = true)
	private LocalDate exitDate;

	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	// @formatter:off
 	@JoinTable(name = "RESOURCE_RESUME_MAP", 
		joinColumns = @JoinColumn(name = "RESOURCE_CODE", referencedColumnName = "CODE", 
		foreignKey = @ForeignKey(name = FK_RESOURCE_RESUME_MAP_RESOURCE_CODE), unique = false), 
		inverseJoinColumns = @JoinColumn(name = "RESUME_ID", referencedColumnName = "ID", 
		foreignKey = @ForeignKey(name = FK_RESOURCE_RESUME_MAP_RESUME_ID), unique = false))
	// @formatter:on
	@OrderBy("uploadDate DESC")
	private Set<Document> resumes = new LinkedHashSet<Document>();

	public void uploadResume() {
		// TODO: Implement later
	}

	public void deleteResume() {
		// TODO: Implement later
	}

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "IMAGE_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_IMAGE_ID))
	private Document image;

	public void uploadImage() {
		// TODO: Implement later
	}

	@NotEmpty(message = MESSAGE_COSTING_MANDATORY)
	// @formatter:off
 	@OneToMany(mappedBy = "resource", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY,
 			targetEntity = com.gspann.itrack.domain.model.business.payments.NonFTECost.class)
 	// @formatter:on
	private List<Costing> costings = new ArrayList<>();

	public void changeCost() {
		// TODO: Implement later
	}

	@NotEmpty(message = MESSAGE_ALLOCATION_MANDATORY)
	@OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
	@Getter(value = AccessLevel.NONE)
	private List<Allocation> allocations = new ArrayList<>();

	public List<Allocation> allocations() {
		return Collections.unmodifiableList(allocations);
	}

	public Allocation allocate(final Money hourlyRate, final Project project, final LocalDate fromDate,
			final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem) {
		// TODO: Implement later
		return project.allocateWith(this).fully()
				.onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem).startingFrom(fromDate)
				.till(tillDate).atHourlyRate(hourlyRate);
	}

	public Allocation allocate(final Billing billRate, final Project project, final LocalDate fromDate,
			final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem) {
		// TODO: Implement later
		return project.allocateWith(this).fully()
				.onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem).startingFrom(fromDate)
				.till(tillDate).atBilling(billRate);
	}

	public Allocation allocateAsBufferIn(final Project project, final LocalDate fromDate, final LocalDate tillDate,
			final Toggle onboardedToClientTimeTrackingsystem) {
		// TODO: Implement later
		return project.allocateWith(this).fully().onboardedToClientTimeTrackingSystem(Toggle.YES)
				.startingFrom(fromDate).till(tillDate).asBuffer();
	}

	public Allocation allocateAsNonBillableIn(final Project project, final LocalDate fromDate, final LocalDate tillDate,
			final Toggle onboardedToClientTimeTrackingsystem) {
		// TODO: Implement later
		return project.allocateWith(this).fully().onboardedToClientTimeTrackingSystem(Toggle.YES)
				.startingFrom(fromDate).till(tillDate).asNonBillable();
	}

	public ResourceAllocationProjectBuilder allocate() {
		return new ResourceAllocationBuilder();
	}

	public interface ResourceAllocationProjectBuilder {
		public ResourceAllocationProportionBuilder in(final Project project);
	}

	public interface ResourceAllocationProportionBuilder {
		public ResourceAllocationClientTimeTrackingBuilder fully();

		public ResourceAllocationClientTimeTrackingBuilder partially(final short percentage);
	}

	public interface ResourceAllocationClientTimeTrackingBuilder {
		public ResourceAllocationStartDateBuilder onboardedToClientTimeTrackingSystem(Toggle yesOrNo);
	}

	public interface ResourceAllocationStartDateBuilder {
		public ResourceAllocationEndDateBuilder startingFrom(final LocalDate startDate);

		public ResourceAllocationBillabilityStatusBuilder startingIndefinatelyFrom(final LocalDate startDate);
	}

	public interface ResourceAllocationEndDateBuilder {
		public ResourceAllocationBillabilityStatusBuilder till(final LocalDate endDate);
	}

	public interface ResourceAllocationBillabilityStatusBuilder {
		public Allocation asBuffer();

		public Allocation asNonBillable();

		public Allocation atHourlyRateOf(final Money hourlyRate);

		public Allocation withBilling(final Billing billing);
	}

	public class ResourceAllocationBuilder implements ResourceAllocationProjectBuilder,
			ResourceAllocationProportionBuilder, ResourceAllocationStartDateBuilder, ResourceAllocationEndDateBuilder,
			ResourceAllocationBillabilityStatusBuilder, ResourceAllocationClientTimeTrackingBuilder {

		private Project project;
		private short proportion;
		private LocalDate allocationStartDate;
		private LocalDate allocationEndDate;
		private Toggle clientTimeTracking;
		private List<Billing> billings = new ArrayList<>();

		@Override
		public ResourceAllocationProportionBuilder in(Project project) {
			this.project = project;
			return this;
		}

		@Override
		public ResourceAllocationClientTimeTrackingBuilder fully() {
			this.proportion = 100;
			return this;
		}

		@Override
		public ResourceAllocationClientTimeTrackingBuilder partially(short percentage) {
			this.proportion = percentage;
			return this;
		}

		@Override
		public ResourceAllocationStartDateBuilder onboardedToClientTimeTrackingSystem(Toggle yesOrNo) {
			this.clientTimeTracking = yesOrNo;
			return this;
		}

		@Override
		public ResourceAllocationEndDateBuilder startingFrom(LocalDate startDate) {
			this.allocationStartDate = startDate;
			return this;
		}

		@Override
		public ResourceAllocationBillabilityStatusBuilder startingIndefinatelyFrom(LocalDate startDate) {
			this.allocationStartDate = startDate;
			return this;
		}

		@Override
		public ResourceAllocationBillabilityStatusBuilder till(LocalDate endDate) {
			this.allocationEndDate = endDate;
			return this;
		}

		@Override
		public Allocation asBuffer() {
			this.billings.add(
					Bill.billing().ofBuffer().startingFrom(this.allocationStartDate).tillDate(this.allocationEndDate));
			Allocation allocation = Allocation.of(Resource.this, this.project, this.proportion,
					DateRange.dateRange().startingOn(allocationStartDate).endingOn(allocationEndDate),
					clientTimeTracking == Toggle.YES ? true : false, billings);
			Resource.this.allocations.add(allocation);
			return allocation;
		}

		@Override
		public Allocation asNonBillable() {
			this.billings.add(Bill.billing().ofNonBillable().startingFrom(this.allocationStartDate)
					.tillDate(this.allocationEndDate));
			Allocation allocation = Allocation.of(Resource.this, this.project, this.proportion,
					DateRange.dateRange().startingOn(allocationStartDate).endingOn(allocationEndDate),
					clientTimeTracking == Toggle.YES ? true : false, billings);
			Resource.this.allocations.add(allocation);
			return allocation;
		}

		@Override
		public Allocation atHourlyRateOf(Money hourlyRate) {
			this.billings.add(Bill.billing().atHourlyRateOf(hourlyRate).startingFrom(this.allocationStartDate)
					.tillDate(this.allocationEndDate));
			Allocation allocation = Allocation.of(Resource.this, this.project, this.proportion,
					DateRange.dateRange().startingOn(allocationStartDate).endingOn(allocationEndDate),
					clientTimeTracking == Toggle.YES ? true : false, billings);
			Resource.this.allocations.add(allocation);
			return allocation;
		}

		@Override
		public Allocation withBilling(Billing billing) {
			this.billings.add(billing);
			Allocation allocation = Allocation.of(Resource.this, this.project, this.proportion,
					DateRange.dateRange().startingOn(allocationStartDate).endingOn(allocationEndDate),
					clientTimeTracking == Toggle.YES ? true : false, billings);
			Resource.this.allocations.add(allocation);
			return allocation;
		}
	}

	@ManyToMany(mappedBy = "resources")
	private Set<SOW> sows = new HashSet<SOW>();

	@OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
	private List<WeeklyTimeSheet> timesheets = new ArrayList<>();

	public static BaseLocationBuilder expectedToJoinOn(final LocalDate expectedJoiningDate) {
		return new ResourceBuilder(expectedJoiningDate);
	}

	public interface BaseLocationBuilder {
		public EmployementTypeBuilder at(final City baseLocation);
	}

	public interface EmployementTypeBuilder {

		public AnnualSalaryBuilder asFullTimeEmployee();

		public NameBuilder asDirectContractor();

		public NameBuilder asSubContractor();

		public NameBuilder asW2Consultant();
	}

	public interface AnnualSalaryBuilder {
		public CommissionBuilder withAnnualSalary(final Money money);

		public NameBuilder withJustAnnualSalary(final Money money);
	}

	public interface CommissionBuilder {
		public BonusBuilder plusCommission(final Money money);

		public BonusBuilder noCommission();
	}

	public interface BonusBuilder {
		public OtherCostBuilder plusBonus(final Money money);

		public OtherCostBuilder noBonus();
	}

	public interface OtherCostBuilder {
		public NameBuilder andOtherCost(final Money money);

		public NameBuilder noOtherCost();
	}

	public interface NonFTECostBuilder {
		public NameBuilder atHourlyCost(final Money money);
	}

	public interface NameBuilder {
		public GenderBuilder withName(final String name);
	}

	public interface GenderBuilder {
		public EmailBuilder male();

		public EmailBuilder female();
	}

	public interface EmailBuilder {
		// public void withEmailAsLogin(final String emailId);
		public GreytHRIDBuilder withEmail(final String emailId);
	}

	public interface GreytHRIDBuilder {
		public DeputedLocationBuilder withGreytHRID(final String greytHRID);

		public DeputedLocationBuilder withNoGreytHRID();
	}

	public interface DeputedLocationBuilder {
		public Resource deputeAt(City deputedLocation);
	}

	public static class ResourceBuilder
			implements BaseLocationBuilder, EmployementTypeBuilder, AnnualSalaryBuilder, CommissionBuilder,
			NonFTECostBuilder, NameBuilder, GenderBuilder, EmailBuilder, GreytHRIDBuilder, DeputedLocationBuilder {
		private Resource resource;

		ResourceBuilder(final LocalDate expectedJoiningDate) {
			this.resource.expectedJoiningDate = expectedJoiningDate;
		}

		@Override
		public EmployementTypeBuilder at(City baseLocation) {
			this.resource.baseLocation = baseLocation;
			return this;
		}

		@Override
		public AnnualSalaryBuilder asFullTimeEmployee() {
			this.resource.employmentType = EmploymentType.fullTimeEmployee();
			return this;
		}

		@Override
		public CommissionBuilder withAnnualSalary(Money money) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NameBuilder withJustAnnualSalary(Money money) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BonusBuilder plusCommission(Money money) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BonusBuilder noCommission() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NameBuilder asDirectContractor() {
			this.resource.employmentType = EmploymentType.directContractor();
			return this;
		}

		@Override
		public NameBuilder asSubContractor() {
			this.resource.employmentType = EmploymentType.subContractor();
			return this;
		}

		@Override
		public NameBuilder asW2Consultant() {
			this.resource.employmentType = EmploymentType.w2Consultant();
			return this;
		}

		@Override
		public NameBuilder atHourlyCost(Money money) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public GenderBuilder withName(String name) {
			this.resource.name = name;
			return this;
		}

		@Override
		public EmailBuilder male() {
			this.resource.gender = Gender.MALE;
			return this;
		}

		@Override
		public EmailBuilder female() {
			this.resource.gender = Gender.FEMALE;
			return this;
		}

		@Override
		public GreytHRIDBuilder withEmail(String emailId) {
			this.resource.emailId = emailId;
			return this;

		}

		@Override
		public DeputedLocationBuilder withGreytHRID(String greytHRID) {
			this.resource.greytHRID = greytHRID;
			return this;
		}

		@Override
		public DeputedLocationBuilder withNoGreytHRID() {
			return this;
		}

		@Override
		public Resource deputeAt(City deputedLocation) {
			this.resource.deputedLocation = deputedLocation;
			return this.resource;
		}
	}
}
