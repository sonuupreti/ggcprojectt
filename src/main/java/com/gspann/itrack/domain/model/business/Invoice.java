package com.gspann.itrack.domain.model.business;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.LocalDate;
import java.time.YearMonth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Columns;
import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.model.common.type.AbstractVersionable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "INVOICES")
public class Invoice extends AbstractVersionable<Long> {

	@Id
	@Column(name = "INVOICE_NUMBER", nullable = false, length = 20)
	private String invoiceNumber;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PO_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_INVOICES_PO_ID))
	private PO po;

	@NotNull
	@Column(name = "DATE", nullable = false)
	private LocalDate date;

	@NotNull
	//@formatter:off
	@Columns(columns = { 
			@Column(name = "CURRENCY", nullable = false, length = 3), 
			@Column(name = "AMOUNT", nullable = false) 
		})
	//@formatter:on
	private Money amount;

	@NotNull
	@Column(name = "SERVICE_MONTH", nullable = false, length = 7)
	private YearMonth serviceMonth;
}
