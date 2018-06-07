package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.gspann.itrack.domain.model.org.skills.Technology;
import com.gspann.itrack.domain.model.org.structure.Practice;

public interface SkillsRepository {

	Practice savePractice(Practice practice);

	Optional<Practice> findPracticeById(Short practiceId);

	Optional<Practice> findPracticeByName(String practiceName);

	List<Practice> findAllPracticees();

	List<Technology> findAllTechnologies();

	Optional<Technology> findTechnologyById(Integer id);
	
	Technology loadTechnologyById(Integer id);
	
	Technology saveTechnology(Technology technology);

	Optional<Technology> findTechnologyByName(String technologyName);
	

}
