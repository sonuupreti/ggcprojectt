package com.gspann.itrack.domain.model.projects;

import java.time.LocalDate;
import java.util.List;

public interface Assignment {

	public void assignOffshoreManager(String projectManager);

	public void assignOnshoreManager(String projectManager);

	public void assignCustomerManager(String projectManager);
	
	public void allocateWith(List<String> resources);
	
	public void scheduleAllocationWith(final LocalDate schedukeOn, final List<String> resources);
	
	public void setPractice(final List<String> practice);
}
