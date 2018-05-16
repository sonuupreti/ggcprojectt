package com.gspann.itrack.domain.model.business;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.docs.Document;
import com.gspann.itrack.domain.model.projects.Project;
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
@Table(name = "SOWS", uniqueConstraints = {
		@UniqueConstraint(name = UNQ_SOW_NUMBER_ACCOUNT_CODE, columnNames = { "SOW_NUMBER", "ACCOUNT_CODE" }) })
public class SOW extends BaseIdentifiableVersionableEntity<Long, Long> {

	@Column(name = "SOW_NUMBER", nullable = false, length = 30)
	private String sowNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_SOWS_ACCOUNT_CODE))
	private Account account;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	// @formatter:off
    @JoinTable(
        name = "SOW_PROJECT_MAP",
        joinColumns = @JoinColumn(name = "SOW_ID", referencedColumnName = "ID", 
        		foreignKey = @ForeignKey(name = FK_SOW_PROJECT_MAP_SOW_ID), unique = false),
        inverseJoinColumns = @JoinColumn(name = "PROJECT_CODE", referencedColumnName = "CODE", 
        		foreignKey = @ForeignKey(name = FK_SOW_PROJECT_MAP_PROJECT_CODE), unique = false)
    )
	// @formatter:on
	private Set<Project> projects = new HashSet<Project>();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	// @formatter:off
    @JoinTable(
        name = "SOW_RESOURCE_MAP",
        joinColumns = @JoinColumn(name = "SOW_ID", referencedColumnName = "ID", 
        		foreignKey = @ForeignKey(name = FK_SOW_RESOURCE_MAP_SOW_ID), unique = false),
        inverseJoinColumns = @JoinColumn(name = "RESOURCE_CODE", referencedColumnName = "CODE", 
        		foreignKey = @ForeignKey(name = FK_SOW_RESOURCE_MAP_RESOURCE_CODE), unique = false)
    )
	// @formatter:on
	private Set<Resource> resources = new HashSet<Resource>();

	private DateRange dateRange;

	@Column(name = "REMARKS", nullable = true, length = 255)
	private String remarks;

	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	// @formatter:off
 	@JoinTable(name = "SOW_DOCUMENT_MAP", 
		joinColumns = @JoinColumn(name = "SOW_ID", referencedColumnName = "ID", 
		foreignKey = @ForeignKey(name = FK_SOW_DOCUMENT_MAP_SOW_ID), unique = false), 
		inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "ID", 
		foreignKey = @ForeignKey(name = FK_SOW_DOCUMENT_MAP_DOCUMENT_ID), unique = false))
	// @formatter:on
	@OrderBy("uploadDate DESC")
	private Set<Document> supportingDocuments = new LinkedHashSet<Document>();

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "parent_sow_id", nullable = true, foreignKey = @ForeignKey(name = FK_SOWS_PARENT_SOW_ID), unique = false)
	private SOW parentSOW;

	@OneToMany(mappedBy = "parentSOW", fetch = FetchType.LAZY)
	private Set<SOW> childSOWs = new HashSet<SOW>();

//	@OneToMany(mappedBy = "sow", fetch = FetchType.LAZY)
//	private List<PO> pos = new ArrayList<PO>();
	

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	// @formatter:off
    @JoinTable(
        name = "SOW_PO_MAP",
        joinColumns = @JoinColumn(name = "SOW_ID", referencedColumnName = "ID", 
        		foreignKey = @ForeignKey(name = FK_SOW_PO_MAP_SOW_ID), unique = false),
        inverseJoinColumns = @JoinColumn(name = "PO_ID", referencedColumnName = "ID", 
        		foreignKey = @ForeignKey(name = FK_SOW_PO_MAP_PO_ID), unique = false)
    )
	// @formatter:on
	private Set<PO> pos = new HashSet<PO>();

}
