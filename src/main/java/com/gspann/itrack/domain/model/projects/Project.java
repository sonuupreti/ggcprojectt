package com.gspann.itrack.domain.model.projects;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.DateRange;
import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.common.type.BaseAutoAssignableVersionableEntity;
import com.gspann.itrack.domain.common.type.Buildable;
import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.business.SOW;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
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

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "PRJ_TYPE_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_PRJ_TYPE_CODE))
	private ProjectType type;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "PRJ_STATUS_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_PRJ_STATUS_CODE))
	private ProjectStatus status;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "LOCATION_ID", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_LOC_ID))
	private City location;

	@NotNull
	private DateRange dateRange;

	@NotNull
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
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
	@Column(name = "TECHNOLOGIES", nullable = false, length = 255)
	private String technologies;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "OFFSHORE_MGR_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_OFFSHORE_MGR_CODE))
	private Resource offshoreManager;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ONSHORE_MGR_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_ONSHORE_MGR_CODE))
	private Resource onshoreManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_CODE", unique = false, nullable = false, foreignKey = @ForeignKey(name = FK_PROJECTS_ACCOUNT_CODE))
	private Account account;

	// Optional but unique
	@Column(name = "CUSTOMER_PROJECT_ID", nullable = true, length = 150)
	private String customerProjectId;

	// Optional but unique
	@Column(name = "CUSTOMER_PROJECT_NAME", nullable = true, length = 150)
	private String customerProjectName;

	@NotNull
	@Column(name = "CUSTOMER_MANAGER", nullable = false, length = 150)
	private String customerManager;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	private List<Allocation> allocations = new ArrayList<>();

    @ManyToMany(mappedBy = "projects")
    private Set<SOW> sows = new HashSet<SOW>();

	public static ProjectTypeBuilder project() {
		return new ProjectBuilder();
	}

	interface ProjectTypeBuilder {
		NameBuilder fixBid();

		NameBuilder TnM();

		NameBuilder milestone();

		NameBuilder investment();

		NameBuilder bench();

		NameBuilder timeoff();
	}

	public interface NameBuilder {
		LocationBuilder namedAs(final String name);
	}

	public interface LocationBuilder {
		AccountBuilder locatedAt(final City location);
	}

	public interface AccountBuilder {
		StartDateBuilder inAccount(final Account Account);
	}

	public interface StartDateBuilder {
		EndDateBuilder startingOn(final LocalDate startDate);

		ProjectPracticeBuilder startingIndefinatelyOn(final LocalDate startDate);
	}

	public interface EndDateBuilder {
		ProjectPracticeBuilder endingOn(final LocalDate endDate);
	}

	public interface ProjectPracticeBuilder {
		TechnologiesBuilder practices(AddPracticeBuilder practice);

		TechnologiesBuilder withPractices(final Set<Practice> practices);
	}

	public interface AddPracticeBuilder {
		AndPracticeBuilder add(final Practice practice);
	}

	public interface AndPracticeBuilder {
		AddPracticeBuilder and();
	}

	public interface TechnologiesBuilder {
		OffshoreManagerBuilder withTechnologies(final String technologies);
	}

	public interface OffshoreManagerBuilder {
		OnshoreManagerBuilder atOffshoreManagedBy(final Resource offshoreManager);
	}

	public interface OnshoreManagerBuilder {
		Project atOnshoreManagedBy(final Resource onshoreManager);
	}

	// public interface CustomerBuilder {
	// TechnologiesBuilder withTechnologies();
	// }

	// public interface AllocationBuilder extends Buildable<Project> {
	// Buildable<Project> withResources(final List<Allocation> allocations);
	// Buildable<Project> withResources(final List<Allocation> allocations);
	// }

	private static class ProjectBuilder implements ProjectTypeBuilder, NameBuilder, LocationBuilder,
			AccountBuilder, StartDateBuilder, EndDateBuilder, ProjectPracticeBuilder, AddPracticeBuilder, AndPracticeBuilder, 
			TechnologiesBuilder, OffshoreManagerBuilder, OnshoreManagerBuilder {
		
		private Project project = new Project();
		private LocalDate startDate;
		private AddPracticeBuilder addPracticeBuilder;

		ProjectBuilder() {
		}

		@Override
		public NameBuilder fixBid() {
			project.type = ProjectType.fixBid();
			return this;
		}

		@Override
		public NameBuilder TnM() {
			project.type = ProjectType.TnM();
			return this;
		}

		@Override
		public NameBuilder milestone() {
			project.type = ProjectType.milestone();
			return this;
		}

		@Override
		public NameBuilder investment() {
			project.type = ProjectType.investment();
			return this;
		}

		@Override
		public NameBuilder bench() {
			project.type = ProjectType.bench();
			return this;
		}

		@Override
		public NameBuilder timeoff() {
			project.type = ProjectType.timeOff();
			return this;
		}

		@Override
		public LocationBuilder namedAs(final String name) {
			project.name = name;
			return this;
		}

		@Override
		public AccountBuilder locatedAt(City location) {
			project.location = location;
			return this;
		}

		@Override
		public StartDateBuilder inAccount(final Account account) {
			project.account = account;
			return this;
		}

		@Override
		public EndDateBuilder startingOn(final LocalDate startDate) {
			this.startDate = startDate;
			return this;
		}

		@Override
		public ProjectPracticeBuilder endingOn(LocalDate endDate) {
			project.dateRange = DateRange.dateRange().startingOn(this.startDate).endingOn(endDate);
			return this;
		}

		@Override
		public ProjectPracticeBuilder startingIndefinatelyOn(LocalDate startDate) {
			project.dateRange = DateRange.dateRange().startIndefinitelyOn(startDate);
			return this;
		}

		@Override
		public TechnologiesBuilder practices(AddPracticeBuilder practice) {
//			add(practice -> practice);
			return this;
		}
		
		@Override
		public AndPracticeBuilder add(Practice practice) {
			project.practices.add(practice);
			return this;
		}

		@Override
		public AddPracticeBuilder and() {
			return this;
		}

		@Override
		public TechnologiesBuilder withPractices(Set<Practice> practices) {
			project.practices = practices;
			return this;
		}

		@Override
		public OffshoreManagerBuilder withTechnologies(String technologies) {
			project.technologies = technologies;
			return this;
		}

		@Override
		public OnshoreManagerBuilder atOffshoreManagedBy(Resource offshoreManager) {
			project.offshoreManager = offshoreManager;
			return this;
		}

		@Override
		public Project atOnshoreManagedBy(Resource onshoreManager) {
			project.onshoreManager = onshoreManager;
			return project;
		}
	}
}
