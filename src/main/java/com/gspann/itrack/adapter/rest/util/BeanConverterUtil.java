package com.gspann.itrack.adapter.rest.util;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.common.dto.AccountDTO;
import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.common.dto.ProjectDTO;
import com.gspann.itrack.domain.model.org.skills.Technology;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.projects.Project;

public class BeanConverterUtil {

	private BeanConverterUtil() {
	}

	private static final Logger log = LoggerFactory.getLogger(BeanConverterUtil.class);

	public static Account accountDTOtoEntity(AccountDTO accountDTO) {

		return null;
	}

	public static AccountDTO accountEntitytoDto(Account account) {

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountCode(account.code());
		accountDTO.setCustomerName(account.customerName());
		accountDTO.setCustomerEntity(account.customerEntity());
		accountDTO.setCustomerReportingManager(account.customerReportingManager());
		accountDTO.setCustomerTimeTracking(account.customerTimeTracking());
		accountDTO.setLocation(account.location().name());
		accountDTO.setAccountManagerCode(account.accountManager().code());
		accountDTO.setAccountManagerName(account.accountManager().name());
		accountDTO.setCityId(account.location().id());
		return accountDTO;
	}

	public static ProjectDTO projectEntitytoDto(Project project) {
		Set<Practice> practices = project.practices();
		List<Pair<String, String>> practiceList = new LinkedList<>();

		Iterator iter = practices.iterator();
		while (iter.hasNext()) {

			Practice object = (Practice) iter.next();
			Pair<String, String> practice = new Pair<String, String>(object.code(), object.name());
			practiceList.add(practice);
		}

		Set<Technology> technologies = project.technologies();
		List<Pair<Integer, String>> technologiesList = new LinkedList<>();

		Iterator iter1 = technologies.iterator();
		while (iter1.hasNext()) {

			Technology object = (Technology) iter1.next();
			Pair<Integer, String> technology = new Pair<Integer, String>(object.id(), object.name());
			technologiesList.add(technology);
		}

		ProjectDTO projectDTO = new ProjectDTO();

		projectDTO.setAccountCode(project.account().code());
		projectDTO.setAccountName(project.account().customerName());
		projectDTO.setLocation(project.location().name());
		projectDTO.setCustomerManager(project.customerManager());
		projectDTO.setCustomerProjectId(project.customerProjectId());
		projectDTO.setCustomerProjectName(project.customerProjectName());
		projectDTO.setOffshoreManagerCode(project.offshoreManager().code());
		projectDTO.setOffshoreManagerName(project.offshoreManager().name());
		projectDTO.setOnshoreManagerCode(project.onshoreManager().code());
		projectDTO.setOnshoreManagerName(project.onshoreManager().name());
		projectDTO.setPracticeList(practiceList);
		projectDTO.setCityId(project.location().id());
		projectDTO.setProjectCode(project.code());
		projectDTO.setProjectName(project.name());
		projectDTO.setProjectStatusCode(project.status().code());
		projectDTO.setProjectStatusDescription(project.status().description());
		projectDTO.setProjectTypeCode(project.type().code());
		projectDTO.setProjectTypeDescription(project.type().description());
		projectDTO.setStartDate(project.dateRange().fromDate());
		projectDTO.setEndDate(project.dateRange().tillDate());
		projectDTO.setTechnologyList(technologiesList);

		return projectDTO;
	}

}
