package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.Department;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;

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
	public EmploymentType saveEmploymentStatus(EmploymentType employmentStatus) {
		Optional<EmploymentType> existingEmploymentStatus = findEmploymentStatusByCode(employmentStatus.code());
		if (existingEmploymentStatus.isPresent()) {
			return entityManager.merge(employmentStatus);
		} else {
			entityManager.persist(employmentStatus);
			return employmentStatus;
		}
	}

	@Override
	public Optional<EmploymentType> findEmploymentStatusByCode(String statusCode) {
		return Optional.ofNullable(entityManager.find(EmploymentType.class, statusCode));
	}

	@Override
	public List<EmploymentType> findAllEmploymentStatuses() {
		return entityManager.createQuery("select e from EmploymentStatus e", EmploymentType.class).getResultList();
	}

	@Override
	public EmploymentStatus saveEngagementStatus(EmploymentStatus engagementStatus) {
		Optional<EmploymentStatus> existingEngagementStatus = findEngagementStatusByCode(engagementStatus.code());
		if (existingEngagementStatus.isPresent()) {
			return entityManager.merge(engagementStatus);
		} else {
			entityManager.persist(engagementStatus);
			return engagementStatus;
		}
	}

	@Override
	public Optional<EmploymentStatus> findEngagementStatusByCode(String statusCode) {
		return Optional.ofNullable(
				entityManager.createQuery("select e from EngagementStatus e where statusCode = '" + statusCode + "'",
						EmploymentStatus.class).getSingleResult());
	}

	@Override
	public List<EmploymentStatus> findAllEngagementStatuses() {
		return entityManager.createQuery("select e from EngagementStatus e", EmploymentStatus.class).getResultList();
	}

}
