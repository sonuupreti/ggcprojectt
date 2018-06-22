package com.gspann.itrack.adapter.persistence.repository;

import static org.junit.Assert.*;

import java.time.LocalDate;
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
import com.gspann.itrack.domain.model.allocations.ResourceProjectAllocationProjection;
import com.gspann.itrack.domain.model.timesheets.Week;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class AllocationRepositoryTest {

	@Autowired
	private AllocationRepository systemUnderTest;

	@Test
	public void testFindAllAllocatedProjectsWithCodeNameAndPercentage() {

		String manojResCode = "20001";
		String rajveerResCode = "20002";
		String ankitResCode = "20003";
		
//		System.out.println(systemUnderTest.findAllAllocationSummaries(rajveerResCode));
		List<ResourceProjectAllocationProjection> allocations = systemUnderTest.findAllAllocationSummaries(manojResCode, Week.of(LocalDate.now()));
		System.out.println(allocations);
	}

}
