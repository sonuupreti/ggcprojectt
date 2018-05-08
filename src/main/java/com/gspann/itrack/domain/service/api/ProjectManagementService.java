package com.gspann.itrack.domain.service.api;

import com.gspann.itrack.domain.model.projects.Project;

public interface ProjectManagementService {

	public void addProject(final Project project);
	
	public void assignOffShoreManager();

	public void assignOnShoreManager();
	
	public void allocateResource();

	public void allocateResources();

	public void scheduleAllocation();
}
