package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gspann.itrack.ItrackApplication;
import com.gspann.itrack.common.constants.ApplicationConstant;
import com.gspann.itrack.domain.model.org.skills.Technology;
import com.gspann.itrack.domain.model.org.structure.Practice;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class SkillsRepositoryTest {
	
	@Autowired
	private SkillsRepository systemUnderTest;

	@Test
	public void testSavePractice() {
//		Practice DevOps = Practice.of("DevOps", "Manoj Nautiyal", "Maven, Gradle, Jenkins, Docker, Promethius");
//		systemUnderTest.savePractice(DevOps);
//		
//		Practice QA = Practice.of("QA", "Ankit Bhardwaj", "JUnit, Salenium, Cucumber, Functional Testing,Integration Testing, Load Testing");
//		systemUnderTest.savePractice(QA);
//		
//		Practice development = Practice.of("Development", "Rajveer Singh", "Java, Spring, Hibernate, AngularJS");
//		systemUnderTest.savePractice(development);
	}
	
/*	@Test
	public void testSaveTechnology() {
		Technology Java = Technology.of("JAVA");
		systemUnderTest.saveTechnology(Java);
		
		Technology Angular = Technology.of("Angular Js");
		systemUnderTest.saveTechnology(Angular);

//		
//		Practice QA = Practice.of("QA", "Ankit Bhardwaj", "JUnit, Salenium, Cucumber, Functional Testing,Integration Testing, Load Testing");
//		systemUnderTest.savePractice(QA);
//		
//		Practice development = Practice.of("Development", "Rajveer Singh", "Java, Spring, Hibernate, AngularJS");
//		systemUnderTest.savePractice(development);
	}*/
	

	@Test
	public void testFindPracticeById() {
	}

	@Test
	public void testFindPracticeByName() {
	}

	@Test
	public void testFindAllPracticees() {
		List<Practice> practices = systemUnderTest.findAllPracticees();
		System.out.println(practices);
	}

}
