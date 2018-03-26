package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.organisation.Company;
import com.gspann.itrack.domain.model.organisation.Department;
import com.gspann.itrack.domain.model.organisation.Designation;
import com.gspann.itrack.domain.model.organisation.EmploymentStatus;
import com.gspann.itrack.domain.model.organisation.EngagementStatus;

@Repository
public class OrganisationRepositoryImpl implements OrganisationRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Company saveCompany(Company company) {
		Optional<Company> existingCompany = findCompanyByName(company.name());
		if (existingCompany.isPresent()) {
			return entityManager.merge(company);
		} else {
			entityManager.persist(company);
			return company;
		}
	}

	@Override
	public Optional<Company> findCompanyById(Short id) {
		return Optional.ofNullable(entityManager.find(Company.class, id));
	}
	
	@Override
	public Optional<Company> findCompanyByName(String name) {
		Company company = null;
		try {
			company = entityManager.createQuery("from Company c where name = '" + name + "'", Company.class)
					.getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(company);
	}

	@Override
	public List<Company> findAllCompanies() {
		return entityManager.createQuery("select c from Company c", Company.class).getResultList();
	}

	@Override
	public Department saveDepartment(Department department) {
		Optional<Department> existingDepartment = findDepartmentByName(department.name());
		if (existingDepartment.isPresent()) {
			return entityManager.merge(department);
		} else {
			entityManager.persist(department);
			return department;
		}
	}

	@Override
	public Optional<Department> findDepartmentById(Short id) {
		return Optional.ofNullable(entityManager.find(Department.class, id));
	}

	@Override
	public Optional<Department> findDepartmentByName(String name) {
		Department department = null;
		try {
			department = entityManager.createQuery("from Department d where name = '" + name + "'", Department.class)
					.getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(department);
	}
	
	@Override
	public List<Department> findAllDepartments() {
		return entityManager.createQuery("from Department d", Department.class).getResultList();
	}

	@Override
	public Designation saveDesignation(Designation designation) {
		Optional<Designation> existingDesignation = findDesignationByName(designation.name());
		if (existingDesignation.isPresent()) {
			return entityManager.merge(designation);
		} else {
			entityManager.persist(designation);
			return designation;
		}
	}

	@Override
	public Optional<Designation> findDesignationById(Short id) {
		return Optional.ofNullable(entityManager.find(Designation.class, id));
	}

	@Override
	public Optional<Designation> findDesignationByName(String name) {
		Designation designation = null;
		try {
			designation = entityManager.createQuery("from Designation d where name = '" + name + "'", Designation.class)
					.getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(designation);
	}
	
	@Override
	public List<Designation> findAllDesignations() {
		return entityManager.createQuery("select d from Designation d", Designation.class).getResultList();
	}

	@Override
	public EmploymentStatus saveEmploymentStatus(EmploymentStatus employmentStatus) {
		Optional<Designation> existingEmploymentStatus = findDesignationById(employmentStatus.id());
		if (existingEmploymentStatus.isPresent()) {
			return entityManager.merge(employmentStatus);
		} else {
			entityManager.persist(employmentStatus);
			return employmentStatus;
		}
	}

	@Override
	public Optional<EmploymentStatus> findEmploymentStatusById(Short id) {
		return Optional.ofNullable(entityManager.find(EmploymentStatus.class, id));
	}

	@Override
	public Optional<EmploymentStatus> findEmploymentStatusByCode(String statusCode) {
		return Optional.ofNullable(
				entityManager.createQuery("select e from EmploymentStatus e where statusCode = '" + statusCode + "'",
						EmploymentStatus.class).getSingleResult());
	}

	@Override
	public List<EmploymentStatus> findAllEmploymentStatuses() {
		return entityManager.createQuery("select e from EmploymentStatus e", EmploymentStatus.class).getResultList();
	}

	@Override
	public EngagementStatus saveEngagementStatus(EngagementStatus employmentStatus) {
		Optional<EngagementStatus> existingEngagementStatus = findEngagementStatusById(employmentStatus.id());
		if (existingEngagementStatus.isPresent()) {
			return entityManager.merge(employmentStatus);
		} else {
			entityManager.persist(employmentStatus);
			return employmentStatus;
		}
	}

	@Override
	public Optional<EngagementStatus> findEngagementStatusById(Short id) {
		return Optional.ofNullable(entityManager.find(EngagementStatus.class, id));
	}

	@Override
	public Optional<EngagementStatus> findEngagementStatusByCode(String statusCode) {
		return Optional.ofNullable(
				entityManager.createQuery("select e from EngagementStatus e where statusCode = '" + statusCode + "'",
						EngagementStatus.class).getSingleResult());
	}

	@Override
	public List<EngagementStatus> findAllEngagementStatuses() {
		return entityManager.createQuery("select e from EngagementStatus e", EngagementStatus.class).getResultList();
	}

}
