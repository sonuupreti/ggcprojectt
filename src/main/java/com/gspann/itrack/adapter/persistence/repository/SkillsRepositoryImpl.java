package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

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
	public Optional<Practice> findPracticeById(Short practiceId) {
		return Optional.ofNullable(entityManager.find(Practice.class, practiceId));
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
	public List<Practice> findAllPracticees() {
		return entityManager.createQuery("from Practice p", Practice.class).getResultList();
	}

}
