package com.gspann.itrack.domain.model.projects.builder;

import com.gspann.itrack.domain.model.projects.Project;

import unquietcode.tools.flapi.Descriptor;
import unquietcode.tools.flapi.DescriptorMaker;
import unquietcode.tools.flapi.Flapi;
//import unquietcode.tools.flapi.examples.email.builder.Email.EmailGenerator;


public class ProjectBuilder implements DescriptorMaker {

	@Override
	public Descriptor descriptor() {
		return Flapi.builder()
			.setPackage("com.gspann.itrack.domain.model.projects.builder")
			.setStartingMethodName("create")
			.setDescriptorName("Project")

			.addMethod("byName(String name)").exactly(1)
			.addMethod("withCustomerProjectId(String customerProjectId)").exactly(1)
			.addMethod("withCustomerProjectName(String customerProjectName)").exactly(1)
//			.addMethod("withType(ProjectType type)").exactly(1)
			.addMethod("onTechnologies(String technologies)").exactly(1)
//			.addMethod("inAccount(Account account)").exactly(1)
//			.addMethod("inPractices(Practice practice)").exactly(1)
//			.addMethod("withOffshoreManager(Resource offshoreManager)").exactly(1)
//			.addMethod("withOnshoreManager(Resource onshoreManager)").exactly(1)
//			.addMethod("withCustomerManager(String customerManager)").exactly(1)
//			.addMethod("atLocation(City location)").exactly(1)
//			.addMethod("startingFrom(Date fromDate)").exactly(1)
//			.addMethod("endingOn(Date fromDate)").atMost(1)
			.addMethod("build()")
			.last(Project.class)
		.build();
	}

//	@Test
//	public void annotated() {
//		Flapi.create(AnnotatedEmailHelper.class)
//			.setPackage("unquietcode.tools.flapi.examples.email.builder")
//			.setStartingMethodName("compose")
//		.build();
//		
//	}

//	@Test
//	public void usage() {
//		EmailMessage message = EmailGenerator.compose(new EmailHelperImpl())
//			.sender("iamthewalrus@hotmail.com")
//			.addRecipient("unclebob@unquietcode.com")
//			.subject("Has you seen my bucket?")
//			.body("Dear sir,\nI was wondering, have you seen my bucket? It is small, metallic, somewhat used, " +
//				  "and slightly smells of fish. Please let me know if you have seen, or ever do see it.\n\nThanks!")
//		.send();
//	}
}