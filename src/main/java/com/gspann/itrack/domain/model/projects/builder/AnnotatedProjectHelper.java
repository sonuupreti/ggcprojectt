package com.gspann.itrack.domain.model.projects.builder;

import com.gspann.itrack.domain.model.projects.Project;

import unquietcode.tools.flapi.annotations.Block;
import unquietcode.tools.flapi.annotations.Exactly;
import unquietcode.tools.flapi.annotations.Last;

@Block(name="Project")
public interface AnnotatedProjectHelper {

	@Exactly(1)
	void byName(String name);

	@Exactly(1)
	void withCustomerProjectId(String customerProjectId);

	@Exactly(1)
	void withCustomerProjectName(String customerProjectName);

	@Exactly(1)
	void onTechnologies(String technologies);

	@Last
	Project build();
}