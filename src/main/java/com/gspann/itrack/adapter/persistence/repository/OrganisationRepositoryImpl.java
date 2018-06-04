package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.business.payments.BillabilityStatus;
import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.Department;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.Practice;

@Repository
public class OrganisationRepositoryImpl implements OrganisationRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Company saveCompany(final Company company) {
		Optional<Company> existingCompany = findCompanyByName(company.name());
		if (existingCompany.isPresent()) {
			return entityManager.merge(company);
		} else {
			entityManager.persist(company);
			return company;
		}
	}

	@Override
	public Optional<Company> findCompanyById(final Short id) {
		return Optional.ofNullable(entityManager.find(Company.class, id));
	}

	@Override
	public Optional<Company> findCompanyByName(final String name) {
		Company company = null;
		try {
			company = entityManager.createQuery("from Company c where name = :name", Company.class)
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(company);
	}

	@Override
	public List<Company> findAllCompanies() {
		return entityManager.createQuery("from Company c", Company.class).getResultList();
	}

	@Override
	public Department saveDepartment(final Department department) {
		Optional<Department> existingDepartment = findDepartmentByNameAndCompany(department.name(),
				department.company().id());
		if (existingDepartment.isPresent()) {
			return entityManager.merge(department);
		} else {
			entityManager.persist(department);
			return department;
		}
	}

	@Override
	public Optional<Department> findDepartmentById(final Short id) {
		return Optional.ofNullable(entityManager.find(Department.class, id));
	}

	@Override
	public Optional<Department> findDepartmentByNameAndCompany(final String name, final short companyId) {
		Department department = null;
		try {
			department = entityManager
					.createQuery("from Department d where d.name = :name and d.company.id= :companyId",
							Department.class)
					.setParameter("name", name).setParameter("companyId", companyId).getSingleResult();
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
	public List<Department> findAllDepartmentsByCompany(final short companyId) {
		return entityManager.createQuery("from Department d where d.company.id = :companyId", Department.class)
				.setParameter("companyId", companyId).getResultList();
	}

	@Override
	public Designation saveDesignation(Designation designation) {
//		Optional<Designation> existingDesignation = findDesignationByName(designation.name());
//		if (existingDesignation.isPresent()) {
//			return entityManager.merge(designation);
//		} else {
			entityManager.persist(designation);
			return designation;
//		}
	}

	@Override
	public Optional<Designation> findDesignationById(final short id) {
		return Optional.ofNullable(entityManager.find(Designation.class, id));
	}

	@Override
	public Optional<Designation> findDesignationByName(final String name) {
		Designation designation = null;
		try {
			designation = entityManager.createQuery("from Designation d where name = :name", Designation.class)
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(designation);
	}

	@Override
	public List<Designation> findAllDesignations() {
		return entityManager.createQuery("from Designation d", Designation.class).getResultList();
	}

	@Override
	public List<Designation> findAllDesignationsByCompany(final short companyId) {
		return entityManager.createQuery("select d from Designation d where d.department.company.id = :companyId",
				Designation.class).setParameter("companyId", companyId).getResultList();
	}

	@Override
	public List<Designation> findAllDesignationsByDepartment(final short departmentId) {
		return entityManager
				.createQuery("select d from Designation d where d.department.id = :departmentId", Designation.class)
				.setParameter("departmentId", departmentId).getResultList();
	}

	@Override
	public List<Designation> findAllDesignationsByDepartmentAndCompany(final short departmentId,
			final short companyId) {
		return entityManager.createQuery("select d from Designation d where d.department.id = :departmentId"
				+ " and d.department.company.id = :companyId", Designation.class).getResultList();
	}
	
	@Override
	public Practice savePractice(final Practice practice) {
		Optional<Practice> existingPractice = findPracticeByName(practice.name());
		if (existingPractice.isPresent()) {
			return entityManager.merge(practice);
		} else {
			entityManager.persist(practice);
			return practice;
		}
	}

	@Override
	public Optional<Practice> findPracticeByCode(final String practiceCode) {
		return Optional.ofNullable(entityManager.find(Practice.class, practiceCode));
	}

	@Override
	public Optional<Practice> findPracticeByName(final String name) {
		Practice practice = null;
		try {
			practice = entityManager.createQuery("from Practice p where name = :name", Practice.class)
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(practice);
	}
	
	@Override
	public List<Practice> findAllPractices() {
		return entityManager.createQuery("from Practice p", Practice.class).getResultList();
	}

	@Override
	public EmploymentType saveEmploymentType(final EmploymentType employmentType) {
		Optional<EmploymentType> existingEmploymentType = findEmploymentTypeByCode(employmentType.code());
		if (existingEmploymentType.isPresent()) {
			return entityManager.merge(employmentType);
		} else {
			entityManager.persist(employmentType);
			return employmentType;
		}
	}

	@Override
	public Optional<EmploymentType> findEmploymentTypeByCode(final String employmentTypeCode) {
		return Optional.ofNullable(entityManager.find(EmploymentType.class, employmentTypeCode));
	}

	@Override
	public List<EmploymentType> findAllEmploymentTypes() {
		return entityManager.createQuery("from EmploymentType e", EmploymentType.class).getResultList();
	}

	@Override
	public EmploymentStatus saveEmploymentStatus(final EmploymentStatus employmentStatus) {
		Optional<EmploymentStatus> existingEmploymentStatus = findEmploymentStatusByCode(employmentStatus.code());
		if (existingEmploymentStatus.isPresent()) {
			return entityManager.merge(employmentStatus);
		} else {
			entityManager.persist(employmentStatus);
			return employmentStatus;
		}
	}

	@Override
	public Optional<EmploymentStatus> findEmploymentStatusByCode(final String employmentStatusCode) {
		return Optional.ofNullable(entityManager.find(EmploymentStatus.class, employmentStatusCode));
	}

	@Override
	public List<EmploymentStatus> findAllEmploymentStatuses() {
		return entityManager.createQuery("from EmploymentStatus e", EmploymentStatus.class).getResultList();
	}

	@Override
	public BillabilityStatus saveBillabilityStatus(final BillabilityStatus billabilityStatus) {
		Optional<BillabilityStatus> existingBillabilityStatus = findBillabilityStatusByCode(billabilityStatus.code());
		if (existingBillabilityStatus.isPresent()) {
			return entityManager.merge(billabilityStatus);
		} else {
			entityManager.persist(billabilityStatus);
			return billabilityStatus;
		}
	}

	@Override
	public Optional<BillabilityStatus> findBillabilityStatusByCode(final String billabilityStatusCode) {
		return Optional.ofNullable(entityManager.find(BillabilityStatus.class, billabilityStatusCode));
	}

	@Override
	public List<BillabilityStatus> findAllBillabilityStatuses() {
		return entityManager.createQuery("from BillabilityStatus b", BillabilityStatus.class).getResultList();
	}
}
