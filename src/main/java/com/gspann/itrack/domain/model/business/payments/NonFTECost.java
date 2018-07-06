package com.gspann.itrack.domain.model.business.payments;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.Hibernate.*;
import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.type.Versionable;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Table(name = "COST_DETAILS")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "COST_RATE_TYPE")
@DiscriminatorValue("NON_FTE_COST_RATE") // CostRateType.NON_FTE_COST_RATE.name()
// Discriminator values : FTE_COST_RATE, NON_FTE_COST_RATE
// to be specified in child classes - FTECost, NonFTECost
public class NonFTECost implements Costing, Versionable<Long> {

	@Setter(value = AccessLevel.NONE)
	@Id
	@GeneratedValue(generator = GLOBAL_SEQ_ID_GENERATOR)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Setter(value = AccessLevel.NONE)
	@Version
	@Access(AccessType.FIELD)
	@Column(name = "VERSION", nullable = false)
	private Long version;

	@NotNull
	private DateRange dateRange;

	@NotNull
	private Payment payment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESOURCE_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_COST_DETAILS_RESOURCE_CODE))
	private Resource resource;


	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
//	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "COST_RATE_TYPE", nullable = false, insertable = false, updatable = false, length = 20)
	// TODO: Apply formula to calculate on the basis of EmploymentStatus of
	// associated resource
	private CostRateType costRateType;

	@Override
	public Payment costRate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Money hourlyCostRate() {
		// TODO Apply the formula to calculate hourly rate
		// if rateUnit is hourly, return rate(); otherwise calculate by formula
		return payment.hourly();
	}

	@Override
	public Money costToCompany() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static NonFTEPayRateBuilder nonFTECostOf(final Resource resource) {
		return new NonFTECostBuilder(resource);
	}
	
	public interface NonFTEPayRateBuilder {
		public NonFTECostingStartDateBuilder atHourlyRate(final Money money);
	}
	
	public interface NonFTECostingStartDateBuilder {
		public NonFTECostingEndDateBuilder startingFrom(final LocalDate fromDate);

		public NonFTECost startingIndefinatelyFrom(final LocalDate fromDate);
	}

	public interface NonFTECostingEndDateBuilder {
		public NonFTECost till(final LocalDate tillDate);
	}

	public static class NonFTECostBuilder implements NonFTEPayRateBuilder, NonFTECostingStartDateBuilder, NonFTECostingEndDateBuilder {
		private NonFTECost cost;
		private LocalDate costStartDate;
		
		NonFTECostBuilder(final Resource resource) {
			this.cost = new NonFTECost();
			this.cost.resource = resource;
		}

		@Override
		public NonFTECostingStartDateBuilder atHourlyRate(Money money) {
			this.cost.payment = Payment.of(PayRateUnit.HOURLY, money);
			return this;
		}

		@Override
		public NonFTECostingEndDateBuilder startingFrom(LocalDate fromDate) {
			this.costStartDate = fromDate;
			return this;
		}

		@Override
		public NonFTECost startingIndefinatelyFrom(LocalDate fromDate) {
			this.cost.dateRange = DateRange.dateRange().startIndefinitelyOn(fromDate);
			return this.cost;
		}

		@Override
		public NonFTECost till(LocalDate tillDate) {
			this.cost.dateRange = DateRange.dateRange().startingOn(this.costStartDate).endingOn(tillDate);
			return this.cost;
		}
	}
	
}
