package com.gspann.itrack.domain.model.business.payments;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.Hibernate.*;
import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

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

import com.gspann.itrack.domain.common.DateRange;
import com.gspann.itrack.domain.common.type.Versionable;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Table(name = "COST_DETAILS")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "COST_RATE_TYPE")
@DiscriminatorValue("NON_FTE_COST_RATE")
// Discriminator values : FTE_COST_RATE, NON_FTE_COST_RATE
// to be specified in child classes - FTECost, NonFTECost
public class NonFTECost implements Costing, Versionable<Long> {

	@Id
	@GeneratedValue(generator = GLOBAL_SEQ_ID_GENERATOR)
	@Column(name = "ID", nullable = false)
	protected Long id;

	@Version
	@Access(AccessType.FIELD)
	@Column(name = "VERSION", nullable = false)
	protected Long version;

	@NotNull
	private DateRange dateRange;

	@NotNull
	private Payment payment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESOURCE_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_COST_DETAILS_RESOURCE_CODE))
	private Resource resource;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "COST_RATE_TYPE", nullable = false, insertable = false, updatable = false, length = 20)
	// TODO: Apply formula to calculate on the basis of EmploymentStatus of
	// associated resource
	private CostRateType costRateType;

	@Override
	public Money hourlyPayment() {
		// TODO Apply the formula to calculate hourly rate
		// if rateUnit is hourly, return rate(); otherwise calculate by formula
		return payment.hourly();
	}

	@Override
	public Money costToCompany() {
		// TODO Auto-generated method stub
		return null;
	}
}
