package com.gspann.itrack.adapter.persistence.repository;

import java.time.Duration;
import java.time.LocalDate;

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
import com.gspann.itrack.common.ApplicationConstant;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.model.timesheets.DailyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.TimeSheetEntry;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class TimeSheetRepositoryTest {

	@Autowired
	private TimeSheetRepository systemUnderTest;

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Test
	public void testSaveTimeSheet() {

		Resource manoj = resourceRepository.getOne("20001");
		Resource rajveer = resourceRepository.getOne("20002");
		Resource ankit = resourceRepository.getOne("20003");

		Project iTrack = projectRepository.getOne("INV0000004");
		Project finalSelect = projectRepository.getOne("INV0000005");

		System.out.println("iTrack -->");

		// WeeklyTimeSheet rajveerWeeklyTimeSheet = WeeklyTimeSheet.of(rajveer)
		// .forWeekOf(Week.of(LocalDate.of(2018, 5, 7))).withDefaultStandardHours()
		// .forMonday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onMonday()
		// .workedOn(iTrack).forDuration(Duration.ofHours(8)).onTasks("Development").dailyComments("Consolidated
		// daily comment").build())
		// .forTuesday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onTuesday()
		// .workedOn(iTrack).forDuration(Duration.ofHours(8)).onTasks("Development").build())
		// .forWednesday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onWednesday()
		// .workedOn(iTrack).forDuration(Duration.ofHours(8)).onTasks("Development").build())
		// .forThursday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onThursday()
		// .workedOn(iTrack).forDuration(Duration.ofHours(8)).onTasks("Development").build())
		// .forFriday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onFriday()
		// .workedOn(iTrack).forDuration(Duration.ofHours(8)).onTasks("Development").build())
		// .forSaturday(DailyTimeSheet.withDefaultStandardHours().forWeekendSaturday(iTrack).build())
		// .forSunday(DailyTimeSheet.withDefaultStandardHours().forWeekendSunday(iTrack).build())
		// .build();
		//
		// systemUnderTest.save(rajveerWeeklyTimeSheet);

		WeeklyTimeSheet manojWeeklyTimeSheet = WeeklyTimeSheet.of(manoj).forWeekOf(Week.of(LocalDate.of(2018, 5, 7)))
				.withDefaultStandardHours()
				.forMonday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onMonday().workedOn(iTrack)
						.forDuration(Duration.ofHours(8)).onTasks("Development")
						.dailyComments("Consolidated daily comment").build())
				.forTuesday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onTuesday().workedOn(iTrack)
						.forDuration(Duration.ofHours(4)).onTasks("Development").and().workedOn(finalSelect)
						.forDuration(Duration.ofHours(4)).onTasks("Testing").build())
				.forWednesday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onWednesday().workedOn(iTrack)
						.forDuration(Duration.ofHours(4)).onTasks("Development").and().workedOn(finalSelect)
						.forDuration(Duration.ofHours(4)).onTasks("Testing").build())
				.forThursday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onThursday().workedOn(iTrack)
						.forDuration(Duration.ofHours(8)).onTasks("Development").build())
				.forFriday(DailyTimeSheet.withDefaultStandardHours().forWorkingDay().onFriday().workedOn(iTrack)
						.forDuration(Duration.ofHours(4)).onTasks("Development").and().workedOn(finalSelect)
						.forDuration(Duration.ofHours(4)).onTasks("Testing").dailyComments("Dual entries").build())
				.forSaturday(DailyTimeSheet.withDefaultStandardHours().forWeekendSaturday(iTrack).build())
				.forSunday(DailyTimeSheet.withDefaultStandardHours().forWeekendSunday(iTrack).build()).build();
		
		 systemUnderTest.save(manojWeeklyTimeSheet);
	}
}