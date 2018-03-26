package com.gspann.itrack.domain.model.project;

import java.time.LocalDate;

import com.gspann.itrack.domain.model.staff.Resource;

public class Allocation {

	private Resource resource;
	
	private Project project;
	
	private short part;

	private LocalDate fromDate;
	
	private LocalDate toDate;
}
