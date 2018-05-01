package com.gspann.itrack.domain.model.business.payments;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.common.DateRange;
import com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.allocations.Allocation;

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
	@JoinColumn(name = "ALLOCATION_ID", foreignKey = @ForeignKey(name = FK_BILLINGS_ALLOCATION_ID))
	private Allocation allocation;

	public static BillabilityStatusBuilder billing() {
		return new BillingBuilder();
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

	public static class BillingBuilder implements BillabilityStatusBuilder, FromDateBuilder, EndDateBuilder {
		private Bill billing = new Bill();
		private LocalDate billingStartDate;

		@Override
		public FromDateBuilder ofBuffer() {
			billing.billabilityStatus = BillabilityStatus.ofBuffer();
			return this;
		}

		@Override
		public FromDateBuilder ofNonBillable() {
			billing.billabilityStatus = BillabilityStatus.ofNonBillable();
			return this;
		}

		@Override
		public FromDateBuilder atHourlyRateOf(Money money) {
			billing.billabilityStatus = BillabilityStatus.ofBillable();
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
