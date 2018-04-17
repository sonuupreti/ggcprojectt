package com.gspann.itrack.domain.model.allocations;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.gspann.itrack.domain.common.DateRange;
import com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.business.payments.Billing;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor()
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "ALLOCATIONS")
public class Allocation extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "RESOURCE_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_ALLOCATIONS_RESOURCE_CODE))
	private Resource resource;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "POJECT_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_ALLOCATIONS_POJECT_CODE))
	private Project project;

	@NotNull
	@Range(min = 0, max = 100) // 0 can be there for TIME_OFF project only otherwise between 1 to 100
	@Column(name = "PROPORTION", nullable = false)
	private short proportion;

	@NotNull
	private DateRange dateRange;
	
	@Column(name = "CUSTOMER_TIME_TRACKING", length = 1)
	@org.hibernate.annotations.Type(type = "yes_no")
	@org.hibernate.annotations.ColumnDefault("'N'")
	private boolean customerTimeTracking = false;

	@NotNull
	// @formatter:off
 	@OneToMany(cascade = CascadeType.ALL, mappedBy = "allocation", 
 		targetEntity = com.gspann.itrack.domain.model.business.payments.Bill.class, fetch = FetchType.LAZY)
	// @formatter:on
	private List<Billing> billings = new ArrayList<>();
}
