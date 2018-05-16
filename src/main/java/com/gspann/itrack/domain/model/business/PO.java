package com.gspann.itrack.domain.model.business;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Columns;
import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.docs.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "POS", indexes = { @Index(name = IDX_POS_PO_NUMBER, columnList = "PO_NUMBER") })
public class PO extends BaseIdentifiableVersionableEntity<Long, Long> {

	@Column(name = "PO_NUMBER", unique = false, nullable = false, length = 30)
	private String poNumber;

//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "SOW_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_POS_SOW_ID))
//	private SOW sow;

	@ManyToMany(mappedBy = "pos")
	private Set<SOW> sows = new HashSet<SOW>();

	@NotNull
	//@formatter:off
	@Columns(columns = { 
			@Column(name = "OPENING_BALANCE_CURRENCY", nullable = false, length = 3), 
			@Column(name = "OPENING_BALANCE_AMOUNT", nullable = false) 
		})
	//@formatter:on
	private Money openingBalance;

	@NotNull
	//@formatter:off
	@Columns(columns = { 
			@Column(name = "AVAILABLE_BALANCE_CURRENCY", nullable = false, length = 3), 
			@Column(name = "AVAILABLE_BALANCE_AMOUNT", nullable = false) 
		})
	//@formatter:on
	private Money availableBalance;

	private DateRange dateRange;

	@Column(name = "REMARKS", nullable = true, length = 255)
	private String remarks;
	
	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	// @formatter:off
 	@JoinTable(name = "PO_DOCUMENT_MAP", 
		joinColumns = @JoinColumn(name = "PO_ID", referencedColumnName = "ID", 
		foreignKey = @ForeignKey(name = FK_PO_DOCUMENT_MAP_PO_ID), unique = false), 
		inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "ID", 
		foreignKey = @ForeignKey(name = FK_PO_DOCUMENT_MAP_DOCUMENT_ID), unique = false))
	// @formatter:on
	@OrderBy("uploadDate DESC")
	private Set<Document> supportingDocuments = new LinkedHashSet<Document>();

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "parent_po_id", nullable = true, foreignKey = @ForeignKey(name = FK_POS_PARENT_PO_ID), unique = false)
	private PO parentPO;

	@OneToMany(mappedBy = "parentPO", fetch = FetchType.LAZY)
	private Set<PO> childPOs = new HashSet<PO>();
	
	@OneToMany(mappedBy = "po", fetch = FetchType.LAZY)
	private List<Invoice> invoices;
}
