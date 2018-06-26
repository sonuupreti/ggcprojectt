package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.allocations.Allocation_;
import com.gspann.itrack.domain.model.allocations.ResourceProjectAllocationProjection;
import com.gspann.itrack.domain.model.common.DateRange_;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.projects.ProjectType;
import com.gspann.itrack.domain.model.projects.ProjectType_;
import com.gspann.itrack.domain.model.projects.Project_;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.model.staff.Resource_;
import com.gspann.itrack.domain.model.timesheets.Week;

@Repository
public class AllocationRepositoryImpl implements AllocationRepositoryJPA {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ResourceProjectAllocationProjection> findAllAllocationSummaries(String resourceCode, final Week week) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ResourceProjectAllocationProjection> query = criteriaBuilder
				.createQuery(ResourceProjectAllocationProjection.class);
		Root<Allocation> allocation = query.from(Allocation.class);
		Join<Allocation, Project> project = allocation.join(Allocation_.project);
		Join<Project, ProjectType> projectType = project.join(Project_.type);
		Join<Allocation, Resource> resource = allocation.join(Allocation_.resource);
		query.select(criteriaBuilder.construct(ResourceProjectAllocationProjection.class,
				resource.get(Resource_.code.getName()), resource.get(Resource_.name.getName()),
				resource.get(Resource_.deputedLocation.getName()), project.get(Project_.code.getName()),
				project.get(Project_.name.getName()),
				allocation.get(Allocation_.dateRange.getName()).get(DateRange_.fromDate.getName()),
				allocation.get(Allocation_.dateRange.getName()).get(DateRange_.tillDate.getName()),
				projectType.get(ProjectType_.code.getName()), projectType.get(ProjectType_.description.getName()),
				allocation.get(Allocation_.proportion.getName()),
				allocation.get(Allocation_.customerTimeTracking.getName())));

		Predicate startDatePredicate = criteriaBuilder.lessThanOrEqualTo(
				allocation.get(Allocation_.dateRange.getName()).get(DateRange_.fromDate.getName()),
				week.startingFrom());
		Predicate endDatePredicate = criteriaBuilder.greaterThanOrEqualTo(
				allocation.get(Allocation_.dateRange.getName()).get(DateRange_.tillDate.getName()), week.endingOn());

		Predicate dateRangePredicate = criteriaBuilder.or(startDatePredicate, endDatePredicate);

		Predicate allocationEndDateNotNullPredicate = criteriaBuilder
				.isNotNull(allocation.get(Allocation_.dateRange.getName()).get(DateRange_.tillDate.getName()));
		Predicate allocationEndDateIsNullPredicate = criteriaBuilder
				.isNull(allocation.get(Allocation_.dateRange.getName()).get(DateRange_.tillDate.getName()));

		Predicate definiteAllocationPredicate = criteriaBuilder.and(allocationEndDateNotNullPredicate,
				dateRangePredicate);
		Predicate indefiniteAllocationPredicate = criteriaBuilder.and(allocationEndDateIsNullPredicate,
				startDatePredicate);

		Predicate allocationDateRangePredicate = criteriaBuilder.or(definiteAllocationPredicate,
				indefiniteAllocationPredicate);

		query.where(criteriaBuilder.and(criteriaBuilder.equal(resource.get(Resource_.code.getName()), resourceCode),
				allocationDateRangePredicate));

		query.orderBy(criteriaBuilder.asc(projectType.get(ProjectType_.code.getName())));
		return entityManager.createQuery(query).getResultList();
	}

}
