package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.skills.Technology;
import com.gspann.itrack.domain.model.org.structure.Practice;

@Repository
public class SkillsRepositoryImpl implements SkillsRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Practice savePractice(Practice practice) {
		Optional<Practice> existingPractice = findPracticeByName(practice.name());
		if (existingPractice.isPresent()) {
			return entityManager.merge(practice);
		} else {
			entityManager.persist(practice);
			return practice;
		}
	}

	@Override
	public Technology saveTechnology(Technology technology) {
		Optional<Technology> existingTechnology = findTechnologyByName(technology.name());
		if (existingTechnology.isPresent()) {
			return entityManager.merge(technology);
		} else {
			entityManager.persist(technology);
			return technology;
		}
	}
	
	@Override
	public Optional<Practice> findPracticeById(Short practiceId) {
		return Optional.ofNullable(entityManager.find(Practice.class, practiceId));
	}

	@Override
	public List<Technology> findAllTechnologies() {
		return entityManager.createQuery("from Technology t", Technology.class).getResultList();
	}
	
	@Override
	public Optional<Technology> findTechnologyById(final Integer id) {
		return Optional.ofNullable(entityManager.find(Technology.class, id));
	}
	
	
	@Override
	public Optional<Practice> findPracticeByName(String practiceName) {
		Practice practice = null;
		try {
			practice = entityManager.createQuery("from Practice p where name = '" + practiceName + "'", Practice.class)
					.getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(practice);
	}
	
	@Override
	public Optional<Technology> findTechnologyByName(String technologyName) {
		Technology technology = null;
		try {
			technology = entityManager.createQuery("from Technology t where name = '" + technologyName + "'", Technology.class)
					.getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(technology);
	}

	public Technology loadTechnologyById(Integer id) {
		return entityManager.getReference(Technology.class, id);
	}
	
	
	@Override
	public List<Practice> findAllPracticees() {
		return entityManager.createQuery("from Practice p", Practice.class).getResultList();
	}

}
