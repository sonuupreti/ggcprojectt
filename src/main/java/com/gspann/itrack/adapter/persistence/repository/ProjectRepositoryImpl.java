package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.projects.ProjectStatus;
import com.gspann.itrack.domain.model.projects.ProjectSummary;
import com.gspann.itrack.domain.model.projects.ProjectType;
import com.gspann.itrack.domain.model.projects.ProjectType_;
import com.gspann.itrack.domain.model.projects.Project_;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.model.staff.Resource_;
import com.gspann.itrack.domain.model.timesheets.Week;

@Repository
public class ProjectRepositoryImpl implements ProjectRepositoryJPA {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ProjectType saveProjectType(final ProjectType projectType) {
		Optional<ProjectType> existingProjectType = findProjectTypeByCode(projectType.code());
		if (existingProjectType.isPresent()) {
			return entityManager.merge(projectType);
		} else {
			entityManager.persist(projectType);
			return projectType;
		}
	}

	@Override
	public Optional<ProjectType> findProjectTypeByCode(final String projectTypeCode) {
		return Optional.ofNullable(entityManager.find(ProjectType.class, projectTypeCode));
	}
	
	@Override
	public Optional<Practice> findPracticeByCode(final String practiceCode) {
		return Optional.ofNullable(entityManager.find(Practice.class, practiceCode));
	}

	@Override
	public List<ProjectType> findAllProjectTypes() {
		return entityManager.createQuery("from ProjectType p", ProjectType.class).getResultList();
	}

	@Override
	public ProjectStatus saveProjectStatus(ProjectStatus projectStatus) {
		Optional<ProjectStatus> existingProjectStatus = findProjectStatusByCode(projectStatus.code());
		if (existingProjectStatus.isPresent()) {
			return entityManager.merge(projectStatus);
		} else {
			entityManager.persist(projectStatus);
			return projectStatus;
		}
	}

	@Override
	public Optional<ProjectStatus> findProjectStatusByCode(String projectStatusCode) {
		return Optional.ofNullable(entityManager.find(ProjectStatus.class, projectStatusCode));
	}

	@Override
	public List<ProjectStatus> findAllProjectStatuses() {
		return entityManager.createQuery("from ProjectStatus p", ProjectStatus.class).getResultList();
	}

	@Override
	public List<Project> findAllBenchProjects() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Project> query = criteriaBuilder.createQuery(Project.class);
		Root<Project> project = query.from(Project.class);
		query.select(project);
		query.where(criteriaBuilder.equal(project.get(Project_.type.getName()).get(Project_.code.getName()),
				ProjectType.CODE.BENCH.value()));
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<Project> findAllLeaveProjects() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Project> query = criteriaBuilder.createQuery(Project.class);
		Root<Project> project = query.from(Project.class);
		query.select(project);
		Predicate paidLeaveTypePredicate = criteriaBuilder.equal(
				project.get(Project_.type.getName()).get(Project_.code.getName()), ProjectType.CODE.PAID_LEAVE.value());
		Predicate unpaidLeaveTypePredicate = criteriaBuilder.equal(
				project.get(Project_.type.getName()).get(Project_.code.getName()),
				ProjectType.CODE.UNPAID_LEAVE.value());
		query.where(criteriaBuilder.or(paidLeaveTypePredicate, unpaidLeaveTypePredicate));
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<ProjectSummary> findProjectSummariesByApproverInAWeek(final Week week, final String approverCode) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectSummary> query = criteriaBuilder.createQuery(ProjectSummary.class);
		
		Root<Project> project = query.from(Project.class);
		Join<Project, ProjectType> projectType = project.join(Project_.type);
		Join<Project, Resource> offshoreManager = project.join(Project_.offshoreManager);
		
		query.select(criteriaBuilder.construct(ProjectSummary.class,
				project.get(Project_.code.getName()), project.get(Project_.name.getName()),
				projectType.get(ProjectType_.code.getName()), projectType.get(ProjectType_.description.getName())));
		
		Predicate paidLeaveTypePredicate = criteriaBuilder.notEqual(
				projectType.get(Project_.code.getName()), ProjectType.CODE.PAID_LEAVE.value());
		Predicate unpaidLeaveTypePredicate = criteriaBuilder.notEqual(
				projectType.get(Project_.code.getName()),
				ProjectType.CODE.UNPAID_LEAVE.value());
		
		Predicate managerEqualsPredicate = criteriaBuilder.equal(
				offshoreManager.get(Resource_.code.getName()), approverCode);
		query.where(criteriaBuilder.and(paidLeaveTypePredicate, unpaidLeaveTypePredicate, managerEqualsPredicate));
		
		return entityManager.createQuery(query).getResultList();
	}

}
