package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.Department;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;

public interface OrganisationRepository {

	Company saveCompany(Company company);

	Optional<Company> findCompanyById(Short id);

	Optional<Company> findCompanyByName(String name);

	List<Company> findAllCompanies();

	Department saveDepartment(Department department);

	Optional<Department> findDepartmentById(Short id);

	Optional<Department> findDepartmentByName(String name);

	List<Department> findAllDepartments();

	Designation saveDesignation(Designation designation);

	Optional<Designation> findDesignationById(Short id);

	Optional<Designation> findDesignationByName(String name);

	List<Designation> findAllDesignations();

	EmploymentType saveEmploymentStatus(EmploymentType employmentStatus);

	Optional<EmploymentType> findEmploymentStatusByCode(String statusCode);

	List<EmploymentType> findAllEmploymentStatuses();

	EmploymentStatus saveEngagementStatus(EmploymentStatus engagementStatus);

	Optional<EmploymentStatus> findEngagementStatusByCode(String statusCode);

	List<EmploymentStatus> findAllEngagementStatuses();
}
