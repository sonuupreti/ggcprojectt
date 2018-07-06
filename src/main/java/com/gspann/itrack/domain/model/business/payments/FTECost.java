package com.gspann.itrack.domain.model.business.payments;

import java.time.LocalDate;

import javax.money.Monetary;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Columns;
import org.javamoney.moneta.Money;

import com.gspann.itrack.common.enums.standard.CurrencyCode;
import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.type.Buildable;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@DiscriminatorValue("FTE_COST_RATE") // CostRateType.FTE_COST_RATE.name()
@SecondaryTable(name = "FTE_COST_DETAILS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ID"))
public class FTECost extends NonFTECost {

	@NotNull
	//@formatter:off
	@Columns(columns = { 
			@Column(table = "FTE_COST_DETAILS", name = "SALARY_CURRENCY", nullable = false, length = 3), 
			@Column(table = "FTE_COST_DETAILS", name = "SALARY_AMOUNT", nullable = false) 
	})
	//@formatter:on
	private Money annualSalary;

//	@NotNull
	//@formatter:off
	@Columns(columns = { 
			@Column(table = "FTE_COST_DETAILS", name = "COMMISSION_CURRENCY", nullable = true, length = 3), 
			@Column(table = "FTE_COST_DETAILS", name = "COMMISSION_AMOUNT", nullable = true) 
	})
	//@formatter:on
	private Money commission;

//	@NotNull
	//@formatter:off
	@Columns(columns = { 
			@Column(table = "FTE_COST_DETAILS", name = "BONUS_CURRENCY", nullable = true, length = 3), 
			@Column(table = "FTE_COST_DETAILS", name = "BONUS_AMOUNT", nullable = true) 
	})
	//@formatter:on
	private Money bonus;

//	@NotNull
	//@formatter:off
	@Columns(columns = { 
			@Column(table = "FTE_COST_DETAILS", name = "OTHER_COST_CURRENCY", nullable = true, length = 3), 
			@Column(table = "FTE_COST_DETAILS", name = "OTHER_COST_AMOUNT", nullable = true) 
	})
	//@formatter:on
	private Money otherCost;

	@Override
	public Money hourlyCostRate() {
		// TODO Apply the formula to calculate hourly rate
		// if rateUnit is hourly, return rate(); otherwise calculate by formula
		return costRate().hourly();
	}

	@Override
	public Money costToCompany() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static FTECostingStartDateBuilder fteCostOf(final Resource resource) {
		return new FTECostBuilder(resource);
	}
	
	public interface FTECostingStartDateBuilder {
		public FTECostingEndDateBuilder startingFrom(final LocalDate fromDate);

		public FTEAnnualSalaryBuilder startingIndefinatelyFrom(final LocalDate fromDate);
	}

	public interface FTECostingEndDateBuilder {
		public FTEAnnualSalaryBuilder till(final LocalDate tillDate);
	}

	public interface FTEAnnualSalaryBuilder {
		public FTEPayRateBuilder withAnnualSalary(final Money annualSalary);
	}

	public interface FTEPayRateBuilder extends Buildable<FTECost> {
		public FTEPayRateBuilder withCommission(final Money commission);
		public FTEPayRateBuilder withBonus(final Money bonus);
		public FTEPayRateBuilder withOtherCost(final Money otherCost);
	}

	public static class FTECostBuilder implements FTECostingStartDateBuilder, FTECostingEndDateBuilder, FTEAnnualSalaryBuilder, FTEPayRateBuilder {
		private FTECost cost;
		private LocalDate costStartDate;
		
		FTECostBuilder(final Resource resource) {
			this.cost = new FTECost();
			this.cost.commission = Money.zero(Monetary.getCurrency(CurrencyCode.INR.name()));
			this.cost.bonus = Money.zero(Monetary.getCurrency(CurrencyCode.INR.name()));
			this.cost.otherCost = Money.zero(Monetary.getCurrency(CurrencyCode.INR.name()));
			this.cost.resource(resource);
		}

		@Override
		public FTECostingEndDateBuilder startingFrom(LocalDate fromDate) {
			this.costStartDate = fromDate;
			return this;
		}

		@Override
		public FTEAnnualSalaryBuilder startingIndefinatelyFrom(LocalDate fromDate) {
			this.cost.dateRange(DateRange.dateRange().startIndefinitelyOn(fromDate));
			return this;
		}

		@Override
		public FTEAnnualSalaryBuilder till(LocalDate tillDate) {
			this.cost.dateRange(DateRange.dateRange().startingOn(this.costStartDate).endingOn(tillDate));
			return this;
		}

		@Override
		public FTEPayRateBuilder withAnnualSalary(Money annualSalary) {
			this.cost.annualSalary = annualSalary;
			return this;
		}
		
		@Override
		public FTEPayRateBuilder withCommission(Money commission) {
			this.cost.commission = commission;
			return this;
		}

		@Override
		public FTEPayRateBuilder withBonus(Money bonus) {
			this.cost.bonus = bonus;
			return this;
		}

		@Override
		public FTEPayRateBuilder withOtherCost(Money otherCost) {
			this.cost.otherCost = otherCost;
			return this;
		}

		@Override
		public FTECost build() {
			// TODO: apply formula to calculate hourly cost from annual salary, commission,
			// bonus and other cost.
			Money totalCost = Money.zero(Monetary.getCurrency(CurrencyCode.INR.name())).add(cost.annualSalary).add(cost.commission).add(cost.bonus).add(cost.otherCost);
			Money hourlyRate = totalCost.divide(Costing.NUMBER_OF_WEEKS_IN_A_YEAR).divide(Costing.STANDARD_WORKING_HOURS_PER_WEEK);
			this.cost.payment(Payment.of(PayRateUnit.HOURLY, hourlyRate));
			return this.cost;
		}
	}
	
    public void updateAnnualSalary(Money annualSalary) {
        this.annualSalary = annualSalary;
    }

    public void updateBonus(Money bonus) {
        this.bonus = bonus;
    }

    public void updateCommission(Money commission) {
        this.commission = commission;
    }

    public void updateOtherCost(Money otherCost) {
        this.otherCost = otherCost;
    }
   
}
