package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.org.holidays.Occasion;

@Repository
public class HolidayCalendarRepositoryImpl implements HolidayCalendarRepositoryJPA {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Occasion saveOccasion(Occasion occasion) {
		Optional<Occasion> existingOccasion = findOccasionByName(occasion.name());
		if (existingOccasion.isPresent()) {
			return entityManager.merge(occasion);
		} else {
			entityManager.persist(occasion);
			return occasion;
		}
	}

	@Override
	public Optional<Occasion> findOccasionByName(String name) {
		Occasion occasion = null;
		try {
			occasion = entityManager.createQuery("from Occasion o where name = :name", Occasion.class)
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(occasion);
	}

	@Override
	public Optional<Occasion> findOccasionById(short id) {
		return Optional.ofNullable(entityManager.find(Occasion.class, id));
	}

	@Override
	public List<Occasion> findAllOccasions() {
		return entityManager.createQuery("from Occasion o", Occasion.class).getResultList();
	}
}
