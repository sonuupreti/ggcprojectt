package com.gspann.itrack.domain.model.staff;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Email;

import com.gspann.itrack.domain.common.BaseAssignableVersionableEntity;
import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.model.business.BillabilityStatus;
import com.gspann.itrack.domain.model.organisation.Company;
import com.gspann.itrack.domain.model.organisation.EmploymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
//@Entity
//@Table(name = "RESOURCES")
public class Resource extends BaseAssignableVersionableEntity<Resource, String, Long> {

	@NotNull
    @Column(name = "LOGIN_ID", nullable = false, length = 100)
    private String loginId;

    @NotNull
    @Column(name = "RESOURCE_NAME", nullable = false, length = 255)
	private String resourceName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", nullable = false, length = 20)
	private Gender gender;

    @Column(name = "GREYT_HR_ID", nullable = true, length = 50)
	private String greytHRID;

    @NaturalId(mutable = false)
    @NotNull
    @Column(name = "EMAIL_ID", nullable = false, length = 255)
    @Email
    private String emailId;
    
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "COMPANY_ID", nullable = false)
	private Company company;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "EMP_STATUS_ID", nullable = false)
    private EmploymentStatus employmentStatus;

	// The base location may not change as of now, but in future the base location may change
    @NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "BASE_LOC_ID", nullable = false)
    private City baseLocation;

    @NotNull
    @Column(name = "PRIMARY_SKILLS", nullable = false, length = 255)
    private String primarySkills;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.PERSIST,  CascadeType.MERGE })
	@JoinColumn(name = "PROFILE_ID", nullable = false)
	private Profile profile;
	
}
