package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.holidays.Holiday;
import com.gspann.itrack.domain.model.org.holidays.HolidayLocation;
import com.gspann.itrack.domain.model.org.holidays.HolidayLocation_;
import com.gspann.itrack.domain.model.org.holidays.Holiday_;
import com.gspann.itrack.domain.model.org.holidays.Occasion;
import com.gspann.itrack.domain.model.timesheets.Week;

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
	
	@Override
	public List<Holiday> findAllHolidaysByWeek(Week week, City location) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Holiday> query = criteriaBuilder.createQuery(Holiday.class);
		Root<Holiday> holiday = query.from(Holiday.class);
		Join<Holiday, HolidayLocation> holidayLocation = holiday.join(Holiday_.locationOcassions);
		query.select(holiday);
		Predicate weekRangePredicate = criteriaBuilder.between(holiday.get(Holiday_.date), week.startingFrom(), week.endingOn());
		Predicate locationIdpredicate = criteriaBuilder.equal(holidayLocation.get(HolidayLocation_.location), location);
		query.where(criteriaBuilder.and(locationIdpredicate, weekRangePredicate));
				
		return entityManager.createQuery(query).getResultList();
	}

}
