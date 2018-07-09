package com.gspann.itrack.domain.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gspann.itrack.adapter.persistence.repository.LocationRepository;
import com.gspann.itrack.adapter.persistence.repository.OrganisationRepository;
import com.gspann.itrack.adapter.persistence.repository.ProjectRepository;
import com.gspann.itrack.adapter.persistence.repository.ResourceRepository;
import com.gspann.itrack.adapter.persistence.repository.SkillsRepository;
import com.gspann.itrack.adapter.rest.util.BeanConverterUtil;
import com.gspann.itrack.common.enums.standard.CurrencyCode;
import com.gspann.itrack.domain.model.business.payments.FTECost;
import com.gspann.itrack.domain.model.business.payments.NonFTECost;
import com.gspann.itrack.domain.model.business.payments.PayRateUnit;
import com.gspann.itrack.domain.model.business.payments.Payment;
import com.gspann.itrack.domain.model.common.Toggle;
import com.gspann.itrack.domain.model.common.dto.CompanyDTO;
import com.gspann.itrack.domain.model.common.dto.DepartmentDTO;
import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.common.dto.ResourceDTO;
import com.gspann.itrack.domain.model.common.dto.ResourceOnBoardingDTO;
import com.gspann.itrack.domain.model.common.dto.ResourceOnLoadVM;
import com.gspann.itrack.domain.model.common.dto.ResourceSearchDTO;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.location.Location;
import com.gspann.itrack.domain.model.org.skills.Technology;
import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.org.structure.ResourceActionType;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.service.api.ResourceManagementService;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResourceManagementServiceImpl implements ResourceManagementService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private OrganisationRepository organizationRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    @Override
    @Transactional
    public ResourceDTO addResource(ResourceDTO resourceDTO) {

        City baseLocation = locationRepository.loadCity(resourceDTO.getBaseLocationId());
        Optional<Designation> designation = organizationRepository.findDesignationById(resourceDTO.getDesignationId());
        Optional<Practice> practice = projectRepository.findPracticeByCode(resourceDTO.getPractice());

        Resource resources = null;
        if (EmploymentType.isFTE(resourceDTO.getEmploymentTypeCode())) {
            resources = Resource.expectedToJoinOn(resourceDTO.getExpectedJoiningDate()).at(baseLocation)
                    .asFullTimeEmployee()
                    .withAnnualSalary(resourceDTO.getAnnualSalary()).plusCommission(resourceDTO.getComission())
                    .plusBonus(resourceDTO.getBonus())
                    .andOtherCost(resourceDTO.getOtherCost(), resourceDTO.getPaystartDate(),
                            resourceDTO.getPayendDate())
                    .withName(resourceDTO.getName()).withGender(resourceDTO.getGender())
                    .onDesignation(designation.get()).withPrimarySkills(resourceDTO.getPrimarySkills())
                    .addPractice(practice.get()).deputeAtJoiningLocation().withEmail(resourceDTO.getEmailId()).build();

        } else {
            resources = Resource.expectedToJoinOn(resourceDTO.getExpectedJoiningDate()).at(baseLocation)
                    .asNonFullTimeEmployee(EmploymentType.getEmploymentType(resourceDTO.getEmploymentTypeCode()))
                    .atHourlyCost(resourceDTO.getRateperHour(), resourceDTO.getPaystartDate(),
                            resourceDTO.getPayendDate())
                    .withName(resourceDTO.getName()).withGender(resourceDTO.getGender())
                    .onDesignation(designation.get()).withPrimarySkills(resourceDTO.getPrimarySkills())
                    .addPractice(practice.get()).deputeAtJoiningLocation().withEmail(resourceDTO.getEmailId()).build();
        }
        resources = resourceRepository.saveAndFlush(resources);
        return BeanConverterUtil.resourceEntitytoDto(resources);
    }

    @Override
    public void onBoardToBench(final String resourceCode, final LocalDate joiningDate) {
        List<Project> benches = projectRepository.findAllBenchProjects();

        Resource resource = resourceRepository.findById(resourceCode).get();
        resource.allocate().in(benches.get(0)).fully().onboardedToClientTimeTrackingSystem(Toggle.NO)
                .startingIndefinatelyFrom(joiningDate).asNonBillable();

        resource.onBoarded(joiningDate, EmploymentStatus.onBench());

        allocateResourceToLeaveProjectsImplicitly(resourceCode, joiningDate);
    }

    @Override
    public void onBoardToProject(final String resourceCode, final String projectCode, final Money hourlyRate,
            final LocalDate joiningDate, final Toggle onboardedToClientTimeTrackingsystem) {

        Resource resource = resourceRepository.findById(resourceCode).get();
        resource.allocate().in(projectRepository.getOne(projectCode)).fully()
                .onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem)
                .startingIndefinatelyFrom(joiningDate).asNonBillable();

        resource.onBoarded(joiningDate, EmploymentStatus.active());

        allocateResourceToLeaveProjectsImplicitly(resourceCode, joiningDate);
    }

    @Override
    public void onBoardToProjectPartially(final String resourceCode, final String projectCode, final short percentage,
            final Money hourlyRate, final LocalDate joiningDate, final Toggle onboardedToClientTimeTrackingsystem) {

        Resource resource = resourceRepository.findById(resourceCode).get();

        resource.allocate().in(projectRepository.getOne(projectCode)).partially(percentage)
                .onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem)
                .startingIndefinatelyFrom(joiningDate).asNonBillable();

        resource.onBoarded(joiningDate, EmploymentStatus.active());

        allocateResourceToLeaveProjectsImplicitly(resourceCode, joiningDate);
    }

    @Override
    public void allocate(final String resourceCode, final String projectCode, final Money hourlyRate,
            final LocalDate fromDate, final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem) {

        Resource resource = resourceRepository.findById(resourceCode).get();

        resource.allocate().in(projectRepository.getOne(projectCode)).fully()
                .onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem).startingFrom(fromDate)
                .till(tillDate).atHourlyRateOf(hourlyRate);

        // resource.markActive();
    }

    @Override
    public void allocatePartially(final String resourceCode, final String projectCode, final short percentage,
            final Money hourlyRate, final LocalDate fromDate, final LocalDate tillDate,
            final Toggle onboardedToClientTimeTrackingsystem) {

        Resource resource = resourceRepository.findById(resourceCode).get();
        resource.allocate().in(projectRepository.getOne(projectCode)).partially(percentage)
                .onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem).startingFrom(fromDate)
                .till(tillDate).atHourlyRateOf(hourlyRate);

        // resource.markActive();

    }

    private void allocateResourceToLeaveProjectsImplicitly(final String resourceCode, final LocalDate joiningDate) {
        List<Project> leaveProjects = projectRepository.findAllLeaveProjects();

        Resource resource = resourceRepository.findById(resourceCode).get();

        for (var leave : leaveProjects) {
            resource.allocate().in(leave).fully().onboardedToClientTimeTrackingSystem(Toggle.NO)
                    .startingIndefinatelyFrom(joiningDate).asNonBillable();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResourceDTO> getAllResources(Pageable pageable) {
        log.debug("Request to get all resources");
        return resourceRepository.findAll(pageable).map(BeanConverterUtil::resourceEntitytoDto);
    }
    @Transactional(readOnly = true)
    public ResourceOnLoadVM resourceOnLodPage() {
        log.debug("Request to load  ResourceOnLoadVM");
        List<Company> companies = organizationRepository.findAllCompanies();
       // List<Department> departments = organizationRepository.findAllDepartments();
        // Collections.sort(companies);
        List<Location> locations = locationRepository.findAllLocations();
        //List<Designation> designations = organizationRepository.findAllDesignations();
        List<Technology> technologies = skillsRepository.findAllTechnologies();

        Collections.sort(locations);

       /* List<Pair<Short, String>> companyPairs = new LinkedList<>();

        for (Company company : companies) {
            Pair<Short, String> comp = new Pair<Short, String>(company.id(), company.name());
            companyPairs.add(comp);
        }*/

        List<Pair<Integer, String>> locationPairs = new LinkedList<>();
        for (Location location : locations) {
            Pair<Integer, String> loc = new Pair<Integer, String>(location.cityId(), location.format());
            locationPairs.add(loc);
        }
       /* List<Pair<Short, String>> departmentPairs = new LinkedList<>();
        for (Department department : departments) {
            Pair<Short, String> dept = new Pair<Short, String>(department.id(), department.name());
            departmentPairs.add(dept);
        }

        List<Pair<Short, String>> designationsPairs = new LinkedList<>();
        for (Designation designation : designations) {
            Pair<Short, String> dept = new Pair<Short, String>(designation.id(), designation.name());
            designationsPairs.add(dept);
        }*/
        List<Pair<Integer, String>> technologiesPairs = new LinkedList<>();
        for (Technology technology : technologies) {
            Pair<Integer, String> tech = new Pair<Integer, String>(technology.id(), technology.name());
            technologiesPairs.add(tech);
        }
        // TODO: need to confirm wheather we need to get it from database
        List<Pair<String, String>> currencyPairs = new LinkedList<>();
        Pair<String, String> inrCurrency = new Pair<String, String>(CurrencyCode.INR.name(), CurrencyCode.INR.name());
        Pair<String, String> gbpCurrency = new Pair<String, String>(CurrencyCode.GBP.name(), CurrencyCode.GBP.name());
        Pair<String, String> usdCurrency = new Pair<String, String>(CurrencyCode.USD.name(), CurrencyCode.USD.name());
        currencyPairs.add(inrCurrency);
        currencyPairs.add(gbpCurrency);
        currencyPairs.add(usdCurrency);

        List<Pair<String, String>> practiceList = projectRepository.findAllPracticeCodeAndDescription();

        Set<CompanyDTO> companyDTOSet = new HashSet<>();
        for (Company company : companies) {
            CompanyDTO companyDTO = new CompanyDTO();

            companyDTO.setCompanyId(company.id());
            companyDTO.setCompanyName(company.name());
            Set<DepartmentDTO> departmentDTOSet = new HashSet<>();

            company.departments().forEach(department -> {
                Set<Pair<Short, String>> designatonsDTOSet = new HashSet<>();
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setDepartmentId(department.id());
                departmentDTO.setDepartmentName(department.name());
                department.designations().forEach(disgnation -> {
                    designatonsDTOSet.add(new Pair<Short, String>(disgnation.id(), disgnation.name()));
                });
                departmentDTO.setDesignations(designatonsDTOSet);
                departmentDTOSet.add(departmentDTO);
            });
            companyDTO.setDepartments(departmentDTOSet);
            companyDTOSet.add(companyDTO);
        }

        return ResourceOnLoadVM.of(locationPairs, technologiesPairs, currencyPairs, practiceList, companyDTOSet);
    }

    @Override
    @Transactional(readOnly = true)
    public ResourceDTO findById(String id) {
        log.debug("Request to get Resource : {}", id);
        Optional<Resource> resource = resourceRepository.findById(id);
        return BeanConverterUtil.resourceEntitytoDto(resource.get());
    }

    @Override
    @Transactional
    public ResourceDTO updateResource(@Valid ResourceDTO resourceDTO) {

        Optional<Resource> resource = resourceRepository.findById(resourceDTO.getResourceCode());
        Resource resourceObject = resource.get();

        City baseLocation = locationRepository.loadCity(resourceDTO.getBaseLocationId());
        City deputedLocation = locationRepository.loadCity(resourceDTO.getDeputedLocationId());
        Designation designation = organizationRepository.findDesignationById(resourceDTO.getDesignationId()).get();
        NonFTECost nonFteCost = resourceObject.costings().get(0);
        if (EmploymentType.isFTE(resourceDTO.getEmploymentTypeCode())) {
            FTECost fteCost = (FTECost) nonFteCost;
            fteCost.updateAnnualSalary(resourceDTO.getAnnualSalary());
            fteCost.updateBonus(resourceDTO.getBonus());
            fteCost.updateCommission(resourceDTO.getComission());
            fteCost.updateOtherCost(resourceDTO.getOtherCost());
            fteCost.dateRange().startOn(resourceDTO.getPaystartDate());
            fteCost.dateRange().endOn(resourceDTO.getPayendDate());
            resourceObject.costings().add(fteCost);
        } else {
            // nonFteCost.updateRateForHour(resourceDTO.getRateperHour());
            nonFteCost.dateRange().startOn(resourceDTO.getPaystartDate());
            nonFteCost.dateRange().endOn(resourceDTO.getPayendDate());
            nonFteCost.payment(Payment.of(PayRateUnit.HOURLY, resourceDTO.getRateperHour()));
            resourceObject.costings().add(nonFteCost);
        }
        resourceObject.updateResourceName(resourceDTO.getName());
        resourceObject.updateEmploymentType(EmploymentType.getEmploymentType(resourceDTO.getEmploymentTypeCode()));
        resourceObject.updateDesignation(designation);
        resourceObject.updateDeputedLocation(deputedLocation);
        resourceObject.updateBaseLocation(baseLocation);
        resourceObject.updateActualJoiningDate(resourceDTO.getActualJoiningDate());
        resourceObject.updateExpectedJoiningDate(resourceDTO.getExpectedJoiningDate());
        resourceObject.updatePrimarySkills(resourceDTO.getPrimarySkills());
        resourceObject.updateSecondarySkills(resourceDTO.getSecondarySkills());

        return BeanConverterUtil.resourceEntitytoDto(resourceObject);
    }

    @Override
    public List<ResourceSearchDTO> getAllResourcesBySearchParameter(String searchParam) {
        log.debug("START:: ResourceManagementServiceImpl :: getAllResourcesBySearchParameter : {}", searchParam);

        String likeExpression = "%" + searchParam.toUpperCase() + "%";

        return resourceRepository.searchResourceByParameter(likeExpression);

    }

    @Override
    @Transactional
    public ResourceOnBoardingDTO processOnBoard(ResourceOnBoardingDTO resourceOnBoardingDTO) {

        Resource resource = resourceRepository.findById(resourceOnBoardingDTO.getResourceCode()).get();

        if (StringUtils.isNotEmpty(resourceOnBoardingDTO.getAction())
                && resourceOnBoardingDTO.getAction().equals(ResourceActionType.JOIN.name())) {

            resource.onBoarded(resourceOnBoardingDTO.getActualJoiningDate(), EmploymentStatus.active());
            resource.updateEmailId(resourceOnBoardingDTO.getEmailId());
            resource.updateGreytHRID(resourceOnBoardingDTO.getGreytHRId());

        } else if (StringUtils.isNotEmpty(resourceOnBoardingDTO.getAction())
                && resourceOnBoardingDTO.getAction().equals(ResourceActionType.DID_NOT_JOIN.name())) {

            resource.markDidNotJoin();
        }

        return BeanConverterUtil.resourceEntitytoOnBoardingDto(resource);
    }

}
