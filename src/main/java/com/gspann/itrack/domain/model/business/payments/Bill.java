package com.gspann.itrack.domain.model.business.payments;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.LocalDate;

import javax.money.Monetary;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.javamoney.moneta.Money;

import com.gspann.itrack.common.enums.standard.CurrencyCode;
import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
@Table(name = "BILLING_DETAILS")
public class Bill extends BaseIdentifiableVersionableEntity<Long, Long> implements Billing {

	@NotNull
	private Payment payment;

	@NotNull
	private DateRange dateRange;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "BILL_STATUS_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_BILLINGS_BILL_STATUS_CODE))
	private BillabilityStatus billabilityStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALLOCATION_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = FK_BILLINGS_ALLOCATION_ID))
	private Allocation allocation;

	public static BillingAllocationBuilder billing() {
		return new BillingBuilder();
	}
	
	public interface BillingAllocationBuilder {
		public BillabilityStatusBuilder forAllocation(final Allocation allocation);
	}

	public interface BillabilityStatusBuilder {
		public FromDateBuilder ofBuffer();
		public FromDateBuilder ofNonBillable();
		public FromDateBuilder atHourlyRateOf(final Money money);
	}

	public interface FromDateBuilder {
		public EndDateBuilder startingFrom(final LocalDate fromDate);

		public Bill startingIndefinatelyOn(final LocalDate fromDate);
	}

	public interface EndDateBuilder {
		public Bill tillDate(final LocalDate tillDate);
	}

	@Override
	public Payment billRate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DateRange dateRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Money hourlyBillRate() {
		// TODO Auto-generated method stub
		return null;
	}

	public static class BillingBuilder implements BillingAllocationBuilder, BillabilityStatusBuilder, FromDateBuilder, EndDateBuilder {
		private Bill billing = new Bill();
		private LocalDate billingStartDate;

		@Override
		public BillabilityStatusBuilder forAllocation(final Allocation allocation) {
			billing.allocation = allocation;
			return this;
		}

		@Override
		public FromDateBuilder ofBuffer() {
			billing.billabilityStatus = BillabilityStatus.ofBuffer();
			billing.payment = Payment.of(PayRateUnit.HOURLY, Money.zero(Monetary.getCurrency(CurrencyCode.INR.name())));
			return this;
		}

		@Override
		public FromDateBuilder ofNonBillable() {
			billing.billabilityStatus = BillabilityStatus.ofNonBillable();
			billing.payment = Payment.of(PayRateUnit.HOURLY, Money.zero(Monetary.getCurrency(CurrencyCode.INR.name())));
			return this;
		}

		@Override
		public FromDateBuilder atHourlyRateOf(Money money) {
			billing.billabilityStatus = BillabilityStatus.ofBillable();
			billing.payment = Payment.of(PayRateUnit.HOURLY, money);
			return this;
		}

		@Override
		public EndDateBuilder startingFrom(LocalDate fromDate) {
			this.billingStartDate = fromDate;
			return this;
		}

		@Override
		public Bill startingIndefinatelyOn(LocalDate fromDate) {
			billing.dateRange = DateRange.dateRange().startIndefinitelyOn(fromDate);
			return this.billing;
		}

		@Override
		public Bill tillDate(LocalDate tillDate) {
			billing.dateRange = DateRange.dateRange().startingOn(billingStartDate).endingOn(tillDate);
			return this.billing;
		}
	}
}
