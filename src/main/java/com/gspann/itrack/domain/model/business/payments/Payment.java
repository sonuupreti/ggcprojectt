package com.gspann.itrack.domain.model.business.payments;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Columns;
import org.javamoney.moneta.Money;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode
@Embeddable
public class Payment {

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "PAY_RATE_UNIT", nullable = false)
	private PayRateUnit payRateUnit;

	@NotNull
	//@formatter:off
	@Columns(columns = { 
			@Column(name = "PAY_RATE_CURRENCY", nullable = false, length = 3), 
			@Column(name = "PAY_RATE_AMOUNT", nullable = false) 
		})
	//@formatter:on
	private Money payMoney;

	public Money hourly() {
		// TODO Apply the formula to calculate hourly rate
		// if rateUnit is hourly, return rate(); otherwise calculate by formula
		return null;
	}

	public PayRateBuilder of(final Money money) {
		return new PaymentBuilder(money);
	}

	public interface PayRateBuilder {
		public Payment atHourlyRate();
	}

	public class PaymentBuilder implements PayRateBuilder {

		private Payment payment;

		public PaymentBuilder(final Money money) {
			this.payment = new Payment();
			this.payment.payMoney = money;
		}

		@Override
		public Payment atHourlyRate() {
			this.payment.payRateUnit = PayRateUnit.HOURLY;
			return this.payment;
		}
	}
	
	public void updatePayMoney(Money payMoney) {
	    this.payMoney = payMoney;
	}
}
