package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.gspann.itrack.domain.model.organisation.Company;
import com.gspann.itrack.domain.model.organisation.Department;
import com.gspann.itrack.domain.model.organisation.Designation;
import com.gspann.itrack.domain.model.organisation.EmploymentStatus;
import com.gspann.itrack.domain.model.organisation.EngagementStatus;

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

	EmploymentStatus saveEmploymentStatus(EmploymentStatus employmentStatus);
	Optional<EmploymentStatus> findEmploymentStatusById(Short id);
	Optional<EmploymentStatus> findEmploymentStatusByCode(String statusCode);
	List<EmploymentStatus> findAllEmploymentStatuses();
	
	EngagementStatus saveEngagementStatus(EngagementStatus employmentStatus);
	Optional<EngagementStatus> findEngagementStatusById(Short id);
	Optional<EngagementStatus> findEngagementStatusByCode(String statusCode);
	List<EngagementStatus> findAllEngagementStatuses();
}
