package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.allocations.Allocation_;
import com.gspann.itrack.domain.model.common.dto.ResourceProjectAllocationSummary;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.projects.ProjectType;
import com.gspann.itrack.domain.model.projects.ProjectType_;
import com.gspann.itrack.domain.model.projects.Project_;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.model.staff.Resource_;

@Repository
public class AllocationRepositoryImpl implements AllocationRepositoryJPA {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ResourceProjectAllocationSummary> findAllAllocationSummaries(String resourceCode) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ResourceProjectAllocationSummary> query = criteriaBuilder
				.createQuery(ResourceProjectAllocationSummary.class);
		Root<Allocation> allocation = query.from(Allocation.class);
		Join<Allocation, Project> project = allocation.join(Allocation_.project);
		Join<Project, ProjectType> projectType = project.join(Project_.type);
		Join<Allocation, Resource> resource = allocation.join(Allocation_.resource);
		query.select(criteriaBuilder.construct(ResourceProjectAllocationSummary.class,
				resource.get(Resource_.code.getName()), resource.get(Resource_.name.getName()),
				resource.get(Resource_.deputedLocation.getName()),
				project.get(Project_.code.getName()), project.get(Project_.name.getName()),
				projectType.get(ProjectType_.code.getName()), projectType.get(ProjectType_.description.getName()),
				allocation.get(Allocation_.proportion.getName()), allocation.get(Allocation_.customerTimeTracking.getName())));
		query.where(criteriaBuilder.equal(resource.get(Resource_.code.getName()), resourceCode));
		query.orderBy(criteriaBuilder.asc(projectType.get(ProjectType_.code.getName())));
		return entityManager.createQuery(query).getResultList();
	}

}
