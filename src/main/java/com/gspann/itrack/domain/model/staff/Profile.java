package com.gspann.itrack.domain.model.staff;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.common.skill.Practice;
import com.gspann.itrack.domain.model.business.Billability;
import com.gspann.itrack.domain.model.business.BillabilityStatus;
import com.gspann.itrack.domain.model.organisation.Department;
import com.gspann.itrack.domain.model.organisation.Designation;
import com.gspann.itrack.domain.model.organisation.EngagementStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
//@Entity
//@Table(name = "PROFILES")
public class Profile extends BaseIdentifiableVersionableEntity<Profile, Long, Long> {

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "DEPARTMENT_ID", nullable = false)
	private Department department;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "DESIGNATION_ID", nullable = false)
	private Designation designation;

	// The deputed location will be the location where the resource is working.
	// It may or may not be same as project location.
	// In case of short term relocation the deputed location will be changed.
	// In case of long term relocation the resource will be terminated from original
	// company
	// and will join as a new resource at new location and hence company.
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "DEPUTED_LOC_ID", nullable = false)
	private City deputedLocation;

	@NotNull
	@Column(name = "SECONDARY_SKILLS", nullable = false, length = 255)
	private String secondarySkills;

	@NotNull
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "PROFILE_PRACTICE", joinColumns = @JoinColumn(name = "PROFILE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PRACTICE_ID", referencedColumnName = "ID"))
	private Set<Practice> practices = new HashSet<Practice>();

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ENG_STATUS_ID", nullable = false)
	private EngagementStatus engagementStatus;

	@NotNull
	@Column(name = "EXPECTED_JOINING_DATE", nullable = false)
	private LocalDate expectedJoiningDate;

	@Column(name = "ACTUAL_JOINING_DATE", nullable = true)
	private LocalDate actualJoiningDate;

	@Column(name = "EXIT_DATE", nullable = true)
	private LocalDate exitDate;

	@Column(name = "CUSTOMER_TIME_TRACKING", length = 1)
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean customerTimeTracking = false;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "BILL_STATUS", nullable = false)
	private BillabilityStatus billStatus;

	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "BILL_STATUS", nullable = true)
	private Billability billability;

	@OneToMany(mappedBy = "Billability", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Billability> billabilities = new HashSet<Billability>();

	// Blob for resume and image
	// @Lob
	// @Column(name = "RESUME", nullable = true)
	// private byte[] resume;

	// @Lob
	// @Column(name = "IMAGE", nullable = true)
	// private byte[] image;
}
