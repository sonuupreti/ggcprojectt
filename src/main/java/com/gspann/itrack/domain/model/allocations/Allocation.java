package com.gspann.itrack.domain.model.allocations;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.model.business.payments.Bill;
import com.gspann.itrack.domain.model.business.payments.Billing;
import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.Toggle;
import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor()
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "ALLOCATIONS")
public class Allocation extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "RESOURCE_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_ALLOCATIONS_RESOURCE_CODE))
	private Resource resource;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "POJECT_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_ALLOCATIONS_POJECT_CODE))
	private Project project;

	@NotNull
	@Range(min = 0, max = 100) // 0 can be there for TIME_OFF project only otherwise between 1 to 100
	@Column(name = "PROPORTION", nullable = false)
	private short proportion;

	@NotNull
	private DateRange dateRange;

	@Column(name = "CUSTOMER_TIME_TRACKING", length = 1)
	private boolean customerTimeTracking = false;

	@NotNull
	// @formatter:off
 	@OneToMany(cascade = CascadeType.ALL, mappedBy = "allocation", 
 		targetEntity = com.gspann.itrack.domain.model.business.payments.Bill.class, fetch = FetchType.LAZY)
	// @formatter:on
	@Getter(value = AccessLevel.NONE)
	private List<Billing> billings = new ArrayList<>();
	
	public List<Billing> billings() {
		return Collections.unmodifiableList(this.billings);
	}
	
	public boolean addBilling(final Billing billing) {
		return this.billings.add(billing);
		// TODO: Update last billing end date
	}
	
	public boolean removeBilling(final Billing billing) {
		return this.billings.remove(billing);
		// TODO: Update last billing end date
	}

	public static ResourceBuilder full() {
		return new AllocationBuilder();
	}

	public static ResourceBuilder partial(final short percentage) {
		return new AllocationBuilder();
	}

	public interface ResourceBuilder {
		public ProjectBuilder of(final Resource resource);
	}

	public interface ProjectBuilder {
		public StartDateBuilder in(final Project project);
	}

	public interface StartDateBuilder {
		public EndDateBuilder startingFrom(final LocalDate startDate);

		public BillabilityBuilder startingIndefinatelyFrom(final LocalDate startDate);
	}

	public interface EndDateBuilder {
		public BillabilityBuilder till(final LocalDate endDate);
	}

	public interface BillabilityBuilder {
		public CustomerTimeTrackingBuilder asBuffer();

		public CustomerTimeTrackingBuilder asNonBillable();

		public CustomerTimeTrackingBuilder atHourlyRateOf(final Money hourlyRate);

		public CustomerTimeTrackingBuilder withBilling(final Billing billing);
	}

	public interface CustomerTimeTrackingBuilder {
		public Allocation onboardedToClientTimeTrackingSystem(Toggle yesOrNo);
	}

	public static class AllocationBuilder implements ResourceBuilder, ProjectBuilder, StartDateBuilder, EndDateBuilder,
			BillabilityBuilder, CustomerTimeTrackingBuilder {
		private Allocation allocation;
		private LocalDate allocationStartDate;

		@Override
		public ProjectBuilder of(Resource resource) {
			this.allocation.resource = resource;
			return this;
		}

		@Override
		public StartDateBuilder in(Project project) {
			this.allocation.project = project;
			return this;
		}

		@Override
		public EndDateBuilder startingFrom(LocalDate startDate) {
			this.allocationStartDate = startDate;
			return this;
		}

		@Override
		public BillabilityBuilder startingIndefinatelyFrom(LocalDate startDate) {
			this.allocation.dateRange = DateRange.dateRange().startIndefinitelyOn(startDate);
			return this;
		}

		@Override
		public BillabilityBuilder till(LocalDate endDate) {
			this.allocation.dateRange = DateRange.dateRange().startingOn(allocationStartDate).endingOn(endDate);
			return this;
		}

		@Override
		public CustomerTimeTrackingBuilder asBuffer() {
			this.allocation.billings.add(Bill.billing().forAllocation(this.allocation).ofBuffer().startingFrom(this.allocation.dateRange.fromDate())
					.tillDate(this.allocation.dateRange.tillDate()));
			return this;
		}

		@Override
		public CustomerTimeTrackingBuilder asNonBillable() {
			this.allocation.billings.add(Bill.billing().forAllocation(this.allocation).ofNonBillable()
					.startingFrom(this.allocation.dateRange.fromDate()).tillDate(this.allocation.dateRange.tillDate()));
			return this;
		}

		@Override
		public CustomerTimeTrackingBuilder atHourlyRateOf(Money hourlyRate) {
			this.allocation.billings.add(Bill.billing().forAllocation(this.allocation).atHourlyRateOf(hourlyRate)
					.startingFrom(this.allocation.dateRange.fromDate()).tillDate(this.allocation.dateRange.tillDate()));
			return this;
		}

		@Override
		public CustomerTimeTrackingBuilder withBilling(Billing billing) {
			this.allocation.billings.add(billing);
			return this;
		}

		@Override
		public Allocation onboardedToClientTimeTrackingSystem(Toggle yesOrNo) {
			this.allocation.customerTimeTracking = yesOrNo == Toggle.YES ? true : false;
			return this.allocation;
		}
	}
}
