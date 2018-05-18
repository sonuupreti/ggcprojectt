package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.location.Country;
import com.gspann.itrack.domain.model.location.Location;
import com.gspann.itrack.domain.model.location.State;

public interface LocationRepository {

	Country saveCountry(Country country);

	Optional<Country> findCountryByCode(String countryCode);

	Optional<Country> findCountryByName(String countryName);

	List<Country> findAllCountries();

	State saveState(State state);

	Optional<State> findStateById(Integer stateId);

	Optional<State> findStateByName(String stateName);

	List<State> findAllStates();

	List<State> findAllStatesByCountryCode(String countryCode);

	City saveCity(City city);

	Optional<City> findCityById(Integer cityId);

	City loadCity(final int cityId);

	Optional<City> findCityByName(String cityName);

	List<City> findAllCities();

	List<City> findAllCitiesByStateId(Integer stateId);

	List<City> findAllCitiesByStateName(String stateName);

	List<City> findAllCitiesByCountryCode(String countryCode);
	
	Optional<Location> findLocationByCityId(final int cityId);
	
	List<Location> findAllLocationsByStateId(final int stateId);
	
	List<Location> findAllLocationsByCountryCode(final String countryCode);
	
	List<Location> findAllLocations();
}
