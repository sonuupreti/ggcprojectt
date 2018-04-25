package com.gspann.itrack.domain.model.staff;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;
import static com.gspann.itrack.common.constants.ValidationConstant.Resource.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.NaturalId;

import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.common.type.BaseAutoAssignableVersionableEntity;
import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.business.SOW;
import com.gspann.itrack.domain.model.business.payments.Costing;
import com.gspann.itrack.domain.model.docs.Document;
import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.Department;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;

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
@Table(name = "RESOURCES", 
		uniqueConstraints = {
				@UniqueConstraint(name = UNQ_RESOURCES_LOGIN_ID, columnNames = {"LOGIN_ID"}),
				@UniqueConstraint(name = UNQ_RESOURCES_EMAIL_ID, columnNames = {"EMAIL_ID"}),
				@UniqueConstraint(name = UNQ_RESOURCES_GREYT_HR_ID, columnNames = {"GREYT_HR_ID"})
		},
		indexes = { 
				@Index(name = IDX_RESOURCES_NAME, columnList = "NAME")    
		}
 )
// @formatter:on
public class Resource extends BaseAutoAssignableVersionableEntity<String, Long> {

	@Pattern(regexp = REGEX_LOGIN_ID, message = MESSAGE_LOGIN_ID_INVALID)
	@NotNull(message = MESSAGE_LOGIN_ID_MANDATORY)
	@NaturalId(mutable = false)
	@Column(name = "LOGIN_ID", nullable = false, length = 100)
	private String loginId;

	@Email(message = MESSAGE_EMAIL_INVALID)
	@NaturalId(mutable = false)
	@Column(name = "EMAIL_ID", nullable = false, length = 255)
	private String emailId;

	@Pattern(regexp = REGEX_GREYT_HR_ID, message = MESSAGE_GREYT_HR_ID_INVALID)
	@Column(name = "GREYT_HR_ID", nullable = true, length = 100)
	private String greytHRID;

	@NotNull
	@Column(name = "NAME", nullable = false, length = 255)
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", nullable = false, length = 10)
	private Gender gender;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "COMPANY_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_COMPANY_CODE))
	private Company company;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "DEPARTMENT_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_DEPARTMENT_ID))
	private Department department;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "DESIGNATION_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_DESIGNATION_ID))
	private Designation designation;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "EMP_TYPE_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_EMP_TYPE_CODE))
	private EmploymentType employmentType;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "EMP_STATUS_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_EMP_STATUS_CODE))
	private EmploymentStatus employmentStatus;

	// The base location may not change as of now, but in future it may change
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "BASE_LOC_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_BASE_LOC_ID))
	private City baseLocation;

	// The deputed location will be the location where the resource is working.
	// It may or may not be same as project location.
	// In case of short term relocation the deputed location will be changed.
	// In case of long term relocation the resource will be terminated from original
	// company and will join as a new resource at new location and hence company.
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "DEPUTED_LOC_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_DEPUTED_LOC_ID))
	private City deputedLocation;

	@NotEmpty(message = MESSAGE_PRIMARY_SKILLS_MANDATORY)
	@Column(name = "PRIMARY_SKILLS", nullable = false, length = 255)
	private String primarySkills;

	@NotEmpty(message = MESSAGE_SECONDARY_SKILLS_MANDATORY)
	@Column(name = "SECONDARY_SKILLS", nullable = false, length = 255)
	private String secondarySkills;

	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	// @formatter:off
 	@JoinTable(name = "RESOURCE_PRACTICE_MAP", 
 		joinColumns = @JoinColumn(name = "RESOURCE_CODE", referencedColumnName = "CODE", 
 		foreignKey = @ForeignKey(name = FK_RESOURCE_PRACTICE_MAP_RESOURCE_CODE), unique = false), 
		inverseJoinColumns = @JoinColumn(name = "PRACTICE_CODE", referencedColumnName = "CODE", 
		foreignKey = @ForeignKey(name = FK_RESOURCE_PRACTICE_MAP_PRACTICE_CODE), unique = false))
	// @formatter:on
	private Set<Practice> practices = new HashSet<Practice>();

	@Column(name = "EXPECTED_JOINING_DATE", nullable = true)
	private LocalDate expectedJoiningDate;

	@Column(name = "ACTUAL_JOINING_DATE", nullable = true)
	private LocalDate actualJoiningDate;

	@Column(name = "EXIT_DATE", nullable = true)
	private LocalDate exitDate;

	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	// @formatter:off
 	@JoinTable(name = "RESOURCE_RESUME_MAP", 
		joinColumns = @JoinColumn(name = "RESOURCE_CODE", referencedColumnName = "CODE", 
		foreignKey = @ForeignKey(name = FK_RESOURCE_RESUME_MAP_RESOURCE_CODE), unique = false), 
		inverseJoinColumns = @JoinColumn(name = "RESUME_ID", referencedColumnName = "ID", 
		foreignKey = @ForeignKey(name = FK_RESOURCE_RESUME_MAP_RESUME_ID), unique = false))
	// @formatter:on
	@OrderBy("uploadDate DESC")
	private Set<Document> resumes = new LinkedHashSet<Document>();

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "IMAGE_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_RESOURCES_IMAGE_ID))
	private Document image;

	@NotEmpty(message = MESSAGE_COSTING_MANDATORY)
	// @formatter:off
 	@OneToMany(mappedBy = "resource", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY,
 			targetEntity = com.gspann.itrack.domain.model.business.payments.NonFTECost.class)
 	// @formatter:on
	private List<Costing> costings = new ArrayList<>();

	@NotEmpty(message = MESSAGE_ALLOCATION_MANDATORY)
	@OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
	private List<Allocation> allocations = new ArrayList<>();

	@ManyToMany(mappedBy = "resources")
	private Set<SOW> sows = new HashSet<SOW>();

	@OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
	private List<WeeklyTimeSheet> timesheets = new ArrayList<>();
}
