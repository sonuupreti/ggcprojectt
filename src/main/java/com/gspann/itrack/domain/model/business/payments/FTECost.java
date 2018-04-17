package com.gspann.itrack.domain.model.business.payments;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Columns;
import org.javamoney.moneta.Money;

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
			@Column(table = "FTE_COST_DETAILS", name = "ANNUAL_SALARY_CURRENCY", nullable = false, length = 3), 
			@Column(table = "FTE_COST_DETAILS", name = "ANNUALSALARY_AMOUNT", nullable = false) 
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
	public Money hourlyPayment() {
		// TODO Apply the formula to calculate hourly rate
		// if rateUnit is hourly, return rate(); otherwise calculate by formula
		return payment().hourly();
	}

	@Override
	public Money costToCompany() {
		// TODO Auto-generated method stub
		return null;
	}
}
