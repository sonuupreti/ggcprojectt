package com.gspann.itrack.domain.model.business.payments;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;
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

	@Override
	public Payment payment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DateRange dateRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Money hourlyPayMoney() {
		// TODO Auto-generated method stub
		return null;
	}
}
