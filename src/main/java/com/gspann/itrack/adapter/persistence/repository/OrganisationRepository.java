package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.gspann.itrack.domain.model.business.payments.BillabilityStatus;
import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.Department;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.Practice;

public interface OrganisationRepository {

	Company saveCompany(final Company company);

	Optional<Company> findCompanyById(final Short id);

	Optional<Company> findCompanyByName(final String name);

	List<Company> findAllCompanies();

	Department saveDepartment(final Department department);

	Optional<Department> findDepartmentById(final Short id);

	Optional<Department> findDepartmentByNameAndCompany(final String name, final short companyId);

	List<Department> findAllDepartments();
	
	List<Department> findAllDepartmentsByCompany(final short companyId);

	Designation saveDesignation(final Designation designation);

	Optional<Designation> findDesignationById(final short id);

	Optional<Designation> findDesignationByName(final String name);
	
	List<Designation> findAllDesignations();

	List<Designation> findAllDesignationsByCompany(final short companyId);

	List<Designation> findAllDesignationsByDepartment(final short departmentId);

	List<Designation> findAllDesignationsByDepartmentAndCompany(final short departmentId, final short companyId);
	
	Practice savePractice(final Practice practice);

	Optional<Practice> findPracticeByCode(final String practiceCode);

	Optional<Practice> findPracticeByName(final String name);
	
	List<Practice> findAllPractices();

	EmploymentType saveEmploymentType(final EmploymentType employmentType);

	Optional<EmploymentType> findEmploymentTypeByCode(final String statusCode);

	List<EmploymentType> findAllEmploymentTypes();

	EmploymentStatus saveEmploymentStatus(final EmploymentStatus employmentStatus);

	Optional<EmploymentStatus> findEmploymentStatusByCode(final String employmentStatusCode);

	List<EmploymentStatus> findAllEmploymentStatuses();
	
	public BillabilityStatus saveBillabilityStatus(final BillabilityStatus billabilityStatus);

	public Optional<BillabilityStatus> findBillabilityStatusByCode(final String billabilityStatusCode);

	public List<BillabilityStatus> findAllBillabilityStatuses();
}
