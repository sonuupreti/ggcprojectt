package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.gspann.itrack.domain.common.skill.Practice;

public interface SkillsRepository {
	
	Practice savePractice(Practice practice);
	Optional<Practice> findPracticeById(Short practiceId);
	Optional<Practice> findPracticeByName(String practiceName);
	List<Practice> findAllPracticees();

}
