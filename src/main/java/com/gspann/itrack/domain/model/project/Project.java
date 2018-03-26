package com.gspann.itrack.domain.model.project;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.BaseAssignableVersionableEntity;
import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.common.skill.Practice;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
//@Entity
//@Table(name = "PROJECT", uniqueConstraints = @UniqueConstraint(name = "UNQ_PRJ_NAME", columnNames = { "NAME" }))
public class Project extends BaseAssignableVersionableEntity<Project, String, Long> {
	
    @NotNull
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    // Optional but unique
    @Column(name = "CUSTOMER_PROJECT_ID", nullable = true, length = 150)
    private String customerProjectId;

    // Optional but unique
    @Column(name = "CUSTOMER_PROJECT_NAME", nullable = true, length = 150)
    private String customerProjectName;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "PRJ_TYPE_CODE", nullable = false)
    private ProjectType projectType;
    
    @NotNull
    @Column(name = "TECHNOLOGIES", nullable = false, length = 255)
    private String technologies;
    
    @NotNull
	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(
	        name = "PROJECT_PRACTICE",
	        joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName="CODE"),
	        inverseJoinColumns = @JoinColumn(name = "PRACTICE_ID", referencedColumnName="ID")
	)
	private Set<Practice> practices = new HashSet<Practice>();
    
    @NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "OFFSHORE_MGR_ID", nullable = false)
    private Resource offshoreManager;

    @NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ONSHORE_MGR_ID", nullable = false)
    private Resource onshoreManager;

    @NotNull
    @Column(name = "CUSTOMER_MANAGER", nullable = false, length = 150)
    private String customerManager;
    
    @NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "LOCATION_ID", nullable = false)
    private City location;
    
    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE", nullable = true)
    private LocalDate endDate;
}
