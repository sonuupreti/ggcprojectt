package com.gspann.itrack.domain.model.business;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.common.type.BaseAutoAssignableVersionableEntity;
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
// @formatter:off
@Table(name = "ACCOUNTS", 
 		uniqueConstraints = @UniqueConstraint(name = UNQ_CUSTOMER_NAME, columnNames = { "CUSTOMER_NAME" }),
 		indexes = { 
 				@Index(name = IDX_CUSTOMER_ENTITY, columnList = "CUSTOMER_ENTITY")  
 		}
)
// @formatter:on
public class Account extends BaseAutoAssignableVersionableEntity<String, Long> {

	@NotNull
	@Column(name = "CUSTOMER_NAME", nullable = false, length = 150)
	private String customerName; // Unique

	// @NotNull
	@Column(name = "CUSTOMER_ENTITY", nullable = true, length = 150)
	private String customerEntity; // customer entity is optional and can be same for multiple accounts

	@NotNull
	@Column(name = "CUSTOMER_REPORTING_MANAGER", nullable = false, length = 150)
	private String customerReportingManager;

	@Column(name = "CUSTOMER_TIME_TRACKING", length = 1)
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean customerTimeTracking = false;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ACCOUNT_MANAGER_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_ACCOUNTS_MGR_RESOURCE_CODE))
	private Resource accountManager;

	// An account will have a single location,
	// in case the customer is at different locations then separate account will be
	// created for each location
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "LOCATION_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_ACCOUNTS_LOCATION_ID))
	private City location;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ACCOUNT_CODE", nullable = false)
	@org.hibernate.annotations.OrderBy(clause = "YEAR desc")
	private Set<Rebate> rebates = new LinkedHashSet<Rebate>();

	@OneToMany(mappedBy = "account")
	private List<Project> projects = new ArrayList<>();
}
