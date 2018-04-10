package com.gspann.itrack.domain.model.projects;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.DateRange;
import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.common.type.BaseAutoAssignableVersionableEntity;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
@Entity
// @formatter:off
@Table(name = "PROJECTS", uniqueConstraints =  {
		@UniqueConstraint(name = UNQ_PROJECTS_NAME, columnNames = { "NAME" }),
		@UniqueConstraint(name = UNQ_PROJECTS_CUSTOMER_PROJECT_ID, columnNames = { "CUSTOMER_PROJECT_ID" }),
		@UniqueConstraint(name = UNQ_PROJECTS_CUSTOMER_PROJECT_NAME, columnNames = { "CUSTOMER_PROJECT_NAME" }),
	}
)
// @formatter:on
public class Project extends BaseAutoAssignableVersionableEntity<String, Long> {
	
    @NotNull
    @Column(name = "NAME", nullable = false, length = 150)
    private String name;

    // Optional but unique
    @Column(name = "CUSTOMER_PROJECT_ID", nullable = true, length = 150)
    private String customerProjectId;

    // Optional but unique
    @Column(name = "CUSTOMER_PROJECT_NAME", nullable = true, length = 150)
    private String customerProjectName;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "PRJ_TYPE_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_PRJ_TYPE_CODE))
    private ProjectType type;
    
    @NotNull
    @Column(name = "TECHNOLOGIES", nullable = false, length = 255)
    private String technologies;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_ACCOUNT_CODE))
    private Account account;
    
    @NotNull
	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	// @formatter:off
 	@JoinTable(
	        name = "PROJECT_PRACTICE_MAP",
	        joinColumns = @JoinColumn(name = "PROJECT_CODE", referencedColumnName="CODE", 
	        foreignKey = @ForeignKey(name = FK_PROJECT_PRACTICE_MAP_PROJECT_CODE), unique = false),
	        inverseJoinColumns = @JoinColumn(name = "PRACTICE_CODE", referencedColumnName="CODE", 
	    	foreignKey = @ForeignKey(name = FK_PROJECT_PRACTICE_MAP_PRACTICE_CODE), unique = false)
	)
    // @formatter:on
	private Set<Practice> practices = new HashSet<Practice>();
    
    @NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "OFFSHORE_MGR_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_OFFSHORE_MGR_CODE))
    private Resource offshoreManager;

    @NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ONSHORE_MGR_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_ONSHORE_MGR_CODE))
    private Resource onshoreManager;

    @NotNull
    @Column(name = "CUSTOMER_MANAGER", nullable = false, length = 150)
    private String customerManager;
    
    @NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "LOCATION_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_LOC_ID))
    private City location;
       
	@NotNull
    private DateRange dateRange;
}
