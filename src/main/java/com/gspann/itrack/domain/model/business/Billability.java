package com.gspann.itrack.domain.model.business;

import java.time.LocalDate;

import com.gspann.itrack.domain.model.project.Project;
import com.gspann.itrack.domain.model.staff.Resource;

public class Billability {

	private Resource resource;
	
	private Project project;
	
	private LocalDate fromDate;
	
	private LocalDate toDate;
}
