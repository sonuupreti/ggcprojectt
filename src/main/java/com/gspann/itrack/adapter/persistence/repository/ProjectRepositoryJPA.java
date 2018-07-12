package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.projects.ProjectStatus;
import com.gspann.itrack.domain.model.projects.ProjectSummary;
import com.gspann.itrack.domain.model.projects.ProjectType;
import com.gspann.itrack.domain.model.timesheets.Week;

public interface ProjectRepositoryJPA {
	

	ProjectType saveProjectType(final ProjectType projectType);

	Optional<ProjectType> findProjectTypeByCode(final String projectTypeCode);

	List<ProjectType> findAllProjectTypes();

	
	ProjectStatus saveProjectStatus(final ProjectStatus projectStatus);

	Optional<ProjectStatus> findProjectStatusByCode(final String projectStatusCode);

	List<ProjectStatus> findAllProjectStatuses();
	
	
	List<Project> findAllBenchProjects();

	List<Project> findAllLeaveProjects();

	Optional<Practice> findPracticeByCode(String practiceCode);

	List<ProjectSummary> findProjectSummariesByApproverInAWeek(final Week week, final String approverCode);
}
