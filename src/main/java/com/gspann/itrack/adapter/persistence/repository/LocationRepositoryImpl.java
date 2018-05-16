package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.location.Country;
import com.gspann.itrack.domain.model.location.Location;
import com.gspann.itrack.domain.model.location.State;

@Repository
public class LocationRepositoryImpl implements LocationRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Country saveCountry(Country country) {
		Optional<Country> existingCountry = findCountryByCode(country.code());
		if (existingCountry.isPresent()) {
			return entityManager.merge(country);
		} else {
			entityManager.persist(country);
			return country;
		}
	}

	@Override
	public Optional<Country> findCountryByCode(String countryCode) {
		return Optional.ofNullable(entityManager.find(Country.class, countryCode));
	}

	@Override
	public Optional<Country> findCountryByName(String countryName) {
		Country country = null;
		try {
			country = entityManager.createQuery("from Country c where name = '" + countryName + "'", Country.class)
					.getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(country);
	}

	@Override
	public List<Country> findAllCountries() {
		return entityManager.createQuery("from Country c", Country.class).getResultList();
	}

	@Override
	public State saveState(State state) {
		Optional<State> existingState = findStateByName(state.name());
		if (existingState.isPresent()) {
			return existingState.get();
		} else {
			entityManager.persist(state);
			return state;
		}
	}

	@Override
	public Optional<State> findStateById(Integer stateId) {
		return Optional.ofNullable(entityManager.find(State.class, stateId));
	}

	@Override
	public Optional<State> findStateByName(String stateName) {
		State state = null;
		try {
			state = entityManager.createQuery("from State s where name = '" + stateName + "'", State.class)
					.getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(state);
	}

	@Override
	public List<State> findAllStates() {
		return entityManager.createQuery("from State s", State.class).getResultList();
	}

	@Override
	public List<State> findAllStatesByCountryCode(String countryCode) {
		return entityManager.createQuery("from State s where s.country.code = :countryCode", State.class)
				.setParameter("countryCode", countryCode).getResultList();
	}

	@Override
	public City saveCity(City city) {
		Optional<City> existingCity = findCityByName(city.name());
		if (existingCity.isPresent()) {
			return existingCity.get();
		} else {
			entityManager.persist(city);
			return city;
		}
	}

	@Override
	public Optional<City> findCityById(Integer cityId) {
		return Optional.ofNullable(entityManager.find(City.class, cityId));
	}

	@Override
	public Optional<City> findCityByName(String cityName) {
		City city = null;
		try {
			city = entityManager.createQuery("from City c where name = '" + cityName + "'", City.class)
					.getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(city);
	}

	@Override
	public List<City> findAllCities() {
		return entityManager.createQuery("from City c", City.class).getResultList();
	}

	public List<City> findAllCitiesByStateId(Integer stateId) {
		return entityManager.createQuery("from City c where c.state.id = :stateId", City.class)
				.setParameter("stateId", stateId).getResultList();
	}

	public List<City> findAllCitiesByStateName(String stateName) {
		return entityManager.createQuery("from City c where c.state.name = :stateName", City.class)
				.setParameter("stateName", stateName).getResultList();
	}

	public List<City> findAllCitiesByCountryCode(String countryCode) {
		return entityManager.createQuery("from City c where c.state.country.code = :countryCode", City.class)
				.setParameter("countryCode", countryCode).getResultList();
	}

	@Override
	public Optional<Location> findLocationByCityId(int cityId) {
		// TODO Auto-generated method stub
		return null;
	}
}
