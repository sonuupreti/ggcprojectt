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

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.business.SOW;
import com.gspann.itrack.domain.model.business.payments.Bill;
import com.gspann.itrack.domain.model.business.payments.Billing;
import com.gspann.itrack.domain.model.business.payments.FTECost;
import com.gspann.itrack.domain.model.business.payments.NonFTECost;
import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.Toggle;
import com.gspann.itrack.domain.model.common.type.BaseAutoAssignableVersionableEntity;
import com.gspann.itrack.domain.model.common.type.Buildable;
import com.gspann.itrack.domain.model.docs.Document;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
// @AllArgsConstructor(staticName = "of")
@Entity
// @formatter:off
@Table(name = "RESOURCES", 
		uniqueConstraints = {
//				@UniqueConstraint(name = UNQ_RESOURCES_LOGIN_ID, columnNames = {"LOGIN_ID"}),
				@UniqueConstraint(name = UNQ_RESOURCES_EMAIL_ID, columnNames = {"EMAIL_ID"}),
				@UniqueConstraint(name = UNQ_RESOURCES_GREYT_HR_ID, columnNames = {"GREYT_HR_ID"})
		},
		indexes = { 
				@Index(name = IDX_RESOURCES_NAME, columnList = "NAME")    
		}
 )
// @formatter:on
public class Resource extends BaseAutoAssignableVersionableEntity<String, Long> {

	// @Pattern(regexp = REGEX_LOGIN_ID, message = MESSAGE_LOGIN_ID_INVALID)
	// @NotNull(message = MESSAGE_LOGIN_ID_MANDATORY)
	// @NaturalId(mutable = false)
	// @Column(name = "LOGIN_ID", nullable = false, length = 100)
	// private String loginId;

	@Email(message = MESSAGE_EMAIL_INVALID)
	// @NaturalId(mutable = false)
	@Column(name = "EMAIL_ID", nullable = true, length = 255)
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
		return (this.employmentType.equals(EmploymentType.fullTimeEmployee())
				|| this.employmentType.equals(EmploymentType.w2Consultant()));
	}

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "EMP_STATUS_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_EMP_STATUS_CODE))
	private EmploymentStatus employmentStatus;

	public void onBoarded(final LocalDate joiningDate, final EmploymentStatus benchOrActive) {
		this.actualJoiningDate = joiningDate;
		this.employmentStatus = benchOrActive;
		// TODO: Need to verify if need to put on bench, if no other project allocation
		// is required or not?
		// TODO: Allocate to supplied project
		// implicitlyAllocateToTimeOffProjects(joiningDate);
	}

	public boolean isOnboarded() {
		return this.employmentStatus.isOnboarded();
	}

	// Instead do that in service class, bcz need to access repository to get
	// time-off projects, which can not be done here
	// public void onBoardToBench(final LocalDate joiningDate) {
	// this.actualJoiningDate = joiningDate;
	// this.employmentStatus = EmploymentStatus.active();
	// // TODO: Allocate to bench project
	//// implicitlyAllocateToTimeOffProjects(joiningDate);
	// }

	// Instead do that in service class, bcz need to access repository to get
	// time-off projects, which can not be done here
	// private void implicitlyAllocateToTimeOffProjects(final LocalDate joiningDate)
	// {
	// // TODO: Allocate to paid leave and unpaid leave, projects, with 0%
	// proportion,
	// // These two projects are of Time-Off type and needs to be handled slight
	// // differently while calculating profit and loss
	// }
	public void markActive() {
		this.employmentStatus = EmploymentStatus.active();
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

	// @NotEmpty(message = MESSAGE_SECONDARY_SKILLS_MANDATORY)
	@Column(name = "SECONDARY_SKILLS", nullable = false, length = 255)
	private String secondarySkills;

	public void updateSecondarySkills(final String newSecondarySkills) {
		this.secondarySkills = newSecondarySkills;
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

	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "IMAGE_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_IMAGE_ID))
	private Document image;

	public void uploadImage(final String filename, final byte[] image) {
		Document document = Document.ofProfileImage(filename, image);
		this.image = document;
	}

	@NotEmpty(message = MESSAGE_COSTING_MANDATORY)
	// @formatter:off
 	@OneToMany(mappedBy = "resource", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
 	// @formatter:on
	// private List<Costing> costings = new ArrayList<>();
	private List<NonFTECost> costings = new ArrayList<>();

	public void changeCost() {
		// TODO: Implement later
	}

	// @NotEmpty(message = MESSAGE_ALLOCATION_MANDATORY)
	@OneToMany(mappedBy = "resource", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
		return project.allocateWith(this).fully().onboardedToClientTimeTrackingSystem(Toggle.YES).startingFrom(fromDate)
				.till(tillDate).asBuffer();
	}

	public Allocation allocateAsNonBillableIn(final Project project, final LocalDate fromDate, final LocalDate tillDate,
			final Toggle onboardedToClientTimeTrackingsystem) {
		// TODO: Implement later
		return project.allocateWith(this).fully().onboardedToClientTimeTrackingSystem(Toggle.YES).startingFrom(fromDate)
				.till(tillDate).asNonBillable();
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
		public void asBuffer();

		public void asNonBillable();

		public void atHourlyRateOf(final Money hourlyRate);

		public void withBilling(final Billing billing);
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
		public void asBuffer() {
			Allocation allocation = Allocation.of(Resource.this, this.project, this.proportion,
					DateRange.dateRange().startingOn(allocationStartDate).endingOn(allocationEndDate),
					clientTimeTracking == Toggle.YES ? true : false, billings);
			this.billings.add(Bill.billing().forAllocation(allocation).ofBuffer().startingFrom(this.allocationStartDate)
					.tillDate(this.allocationEndDate));
			Resource.this.allocations.add(allocation);
		}

		@Override
		public void asNonBillable() {
			Allocation allocation = Allocation.of(Resource.this, this.project, this.proportion,
					DateRange.dateRange().startingOn(allocationStartDate).endingOn(allocationEndDate),
					clientTimeTracking == Toggle.YES ? true : false, billings);
			Resource.this.allocations.add(allocation);
			this.billings.add(Bill.billing().forAllocation(allocation).ofNonBillable()
					.startingFrom(this.allocationStartDate).tillDate(this.allocationEndDate));
		}

		@Override
		public void atHourlyRateOf(Money hourlyRate) {
			Allocation allocation = Allocation.of(Resource.this, this.project, this.proportion,
					DateRange.dateRange().startingOn(allocationStartDate).endingOn(allocationEndDate),
					clientTimeTracking == Toggle.YES ? true : false, billings);
			this.billings.add(Bill.billing().forAllocation(allocation).atHourlyRateOf(hourlyRate)
					.startingFrom(this.allocationStartDate).tillDate(this.allocationEndDate));
			// If allocating fully, then end previous allocation
			Resource.this.allocations.add(allocation);
		}

		@Override
		public void withBilling(Billing billing) {
			this.billings.add(billing);
			Allocation allocation = Allocation.of(Resource.this, this.project, this.proportion,
					DateRange.dateRange().startingOn(allocationStartDate).endingOn(allocationEndDate),
					clientTimeTracking == Toggle.YES ? true : false, billings);
			Resource.this.allocations.add(allocation);
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

		public NonFTECostBuilder asDirectContractor();

		public NonFTECostBuilder asSubContractor();

		public NonFTECostBuilder asW2Consultant();

		public NonFTECostBuilder asNonFullTimeEmployee(EmploymentType employmentType);
	}

	public interface AnnualSalaryBuilder {
		public CommissionBuilder withAnnualSalary(final Money annualSalary);

		public NameBuilder withJustAnnualSalary(final Money annualSalary);
	}

	public interface CommissionBuilder {
		public BonusBuilder plusCommission(final Money commission);

		public BonusBuilder noCommission();
	}

	public interface BonusBuilder {
		public OtherCostBuilder plusBonus(final Money bonus);

		public OtherCostBuilder noBonus();
	}

	public interface OtherCostBuilder {
		public NameBuilder andOtherCost(final Money otherCost, LocalDate startdate, LocalDate endDate);

		public NameBuilder noOtherCost();
	}

	public interface NonFTECostBuilder {
		public NameBuilder atHourlyCost(final Money hourlyCost, LocalDate startdate, LocalDate endDate);
	}

	public interface NameBuilder {
		public GenderBuilder withName(final String name);
	}

	public interface GenderBuilder {

		public DesignationBuilder withGender(final Gender gender);

		public DesignationBuilder male();

		public DesignationBuilder female();
	}

	public interface DesignationBuilder {
		public ResourcePrimarySkillsBuilder onDesignation(final Designation designation);
	}

	/*
	 * public interface EmailBuilder { public ResourcePrimarySkillsBuilder
	 * withEmail(final String emailId); }
	 */

	public interface ResourcePrimarySkillsBuilder {
		public ResourcePracticeBuilder withPrimarySkills(final String primarySkills);
	}

	public interface ResourcePracticeBuilder extends DeputedLocationBuilder {
		public ResourcePracticeBuilder addPractice(final Practice practice);
	}

	public interface DeputedLocationBuilder {
		public OptionalPropertiesBuilder deputeAt(City deputedLocation);

		public OptionalPropertiesBuilder deputeAtJoiningLocation();
	}

	public interface OptionalPropertiesBuilder extends Buildable<Resource> {

		public Buildable<Resource> withGreytHRID(final String greytHRID);

		public Buildable<Resource> withSecondarySkills(final String secondarySkills);

		public Buildable<Resource> withEmail(final String emailId);
	}

	public static class ResourceBuilder implements BaseLocationBuilder, EmployementTypeBuilder, AnnualSalaryBuilder,
			CommissionBuilder, BonusBuilder, OtherCostBuilder, NonFTECostBuilder, NameBuilder, GenderBuilder,
			DesignationBuilder, ResourcePrimarySkillsBuilder, ResourcePracticeBuilder, DeputedLocationBuilder,
			OptionalPropertiesBuilder, Buildable<Resource> {
		private Resource resource;
		private Money annualSalary;
		private Money commission;
		private Money bonus;
		// private Money hourlyRate;

		ResourceBuilder(final LocalDate expectedJoiningDate) {
			this.resource = new Resource();
			this.resource.employmentStatus = EmploymentStatus.pending();
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
		public CommissionBuilder withAnnualSalary(Money annualSalary) {
			this.annualSalary = annualSalary;
			return this;
		}

		@Override
		public NameBuilder withJustAnnualSalary(Money annualSalary) {
			this.resource.costings.add(FTECost.fteCostOf(this.resource).startingIndefinatelyFrom(LocalDate.now())
					.withAnnualSalary(annualSalary).build());
			return this;
		}

		@Override
		public BonusBuilder plusCommission(Money commission) {
			this.commission = commission;
			return this;
		}

		@Override
		public BonusBuilder noCommission() {
			return this;
		}

		@Override
		public OtherCostBuilder plusBonus(Money bonus) {
			this.bonus = bonus;
			return this;
		}

		@Override
		public OtherCostBuilder noBonus() {
			return this;
		}

		@Override
		public NameBuilder andOtherCost(Money otherCost, LocalDate startdate, LocalDate endDate) {
			this.resource.costings.add(FTECost.fteCostOf(this.resource).startingFrom(startdate).till(endDate)
					.withAnnualSalary(annualSalary).withCommission(commission).withBonus(bonus).withOtherCost(otherCost)
					.build());
			return this;
		}

		@Override
		public NameBuilder noOtherCost() {
			this.resource.costings.add(FTECost.fteCostOf(this.resource).startingIndefinatelyFrom(LocalDate.now())
					.withAnnualSalary(annualSalary).withCommission(commission).withBonus(bonus).build());
			return this;
		}

		@Override
		public NonFTECostBuilder asDirectContractor() {
			this.resource.employmentType = EmploymentType.directContractor();
			return this;
		}

		@Override
		public NonFTECostBuilder asSubContractor() {
			this.resource.employmentType = EmploymentType.subContractor();
			return this;
		}

		@Override
		public NonFTECostBuilder asW2Consultant() {
			this.resource.employmentType = EmploymentType.w2Consultant();
			return this;
		}

		@Override
		public NonFTECostBuilder asNonFullTimeEmployee(EmploymentType employmentType) {
			if (employmentType.isFTE()) {
				throw new IllegalArgumentException(
						EmploymentType.fullTimeEmployee() + " is invalid argument to build a Non-FTE resource object, "
						+ "Expected arguments: contractor or sub-contractor");
			}
			this.resource.employmentType = employmentType;
			return this;
		}

		@Override
		public NameBuilder atHourlyCost(Money hourlyCost, LocalDate startdate, LocalDate endDate) {
			this.resource.costings.add(NonFTECost.nonFTECostOf(this.resource).atHourlyRate(hourlyCost)
					.startingFrom(startdate).till(endDate));
			return this;
		}

		@Override
		public GenderBuilder withName(String name) {
			this.resource.name = name;
			return this;
		}

		@Override
		public DesignationBuilder withGender(Gender gender) {
			this.resource.gender = gender;
			return this;
		}

		@Override
		public DesignationBuilder male() {
			this.resource.gender = Gender.MALE;
			return this;
		}

		@Override
		public DesignationBuilder female() {
			this.resource.gender = Gender.FEMALE;
			return this;
		}

		@Override
		public ResourcePrimarySkillsBuilder onDesignation(final Designation designation) {
			this.resource.designation = designation;
			return this;
		}

		@Override
		public Buildable<Resource> withEmail(String emailId) {
			this.resource.emailId = emailId;
			return this;

		}

		@Override
		public ResourcePracticeBuilder withPrimarySkills(String primarySkills) {
			this.resource.primarySkills = primarySkills;
			return this;
		}

		@Override
		public ResourcePracticeBuilder addPractice(Practice practice) {
			this.resource.practices.add(practice);
			return this;
		}

		@Override
		public OptionalPropertiesBuilder deputeAt(City deputedLocation) {
			this.resource.deputedLocation = deputedLocation;
			return this;
		}

		@Override
		public OptionalPropertiesBuilder deputeAtJoiningLocation() {
			this.resource.deputedLocation = this.resource.baseLocation;
			return this;
		}

		@Override
		public Buildable<Resource> withGreytHRID(String greytHRID) {
			this.resource.greytHRID = greytHRID;
			return this;
		}

		@Override
		public Buildable<Resource> withSecondarySkills(String secondarySkills) {
			this.resource.secondarySkills = secondarySkills;
			return this;
		}

		@Override
		public Resource build() {
			return this.resource;
		}
	}

	// update methods

	public void updateEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	public void updateExpectedJoiningDate(LocalDate expectedJoiningDate) {

		this.expectedJoiningDate = expectedJoiningDate;
	}

	public void updateActualJoiningDate(LocalDate actualJoiningDate) {
		this.actualJoiningDate = actualJoiningDate;
	}

	public void updateBaseLocation(City baseLocation) {
		this.baseLocation = baseLocation;
	}

	public void updateDeputedLocation(City deputedLocation) {
		this.deputedLocation = deputedLocation;
	}

	public void updateResourceName(String name) {
		this.name = name;
	}

	public void updateCosting(List<NonFTECost> costings) {
		this.costings = costings;
	}

	public void updateDesignation(Designation designation) {
		this.designation = designation;
	}

	public void updateEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void updateGreytHRID(String greytHRID) {
		this.greytHRID = greytHRID;
	}

}
