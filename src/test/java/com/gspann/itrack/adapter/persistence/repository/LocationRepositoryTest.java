package com.gspann.itrack.adapter.persistence.repository;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

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
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.location.Country;
import com.gspann.itrack.domain.model.location.State;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class LocationRepositoryTest {

	@Autowired
	private LocationRepository systemUnderTest;

	@Test
	public void test01SaveCountry() {

		Country india = Country.of("IN", "India");
		systemUnderTest.saveCountry(india);

		Country usa = Country.of("US", "United States of America");
		systemUnderTest.saveCountry(usa);

		Country uk = Country.of("GB", "United Kingdom");
		systemUnderTest.saveCountry(uk);

	}

	@Test
	public void test02FindCountryByCode() {
		Optional<Country> country = systemUnderTest.findCountryByCode("IN");
		assertTrue(country.isPresent());
	}

	@Test
	public void test03FindAllCountries() {
		List<Country> countries = systemUnderTest.findAllCountries();
		assertTrue(countries.size() == 3);
	}

	@Test
	public void test04SaveState() {
		State haryana = State.of("Haryana", systemUnderTest.findCountryByCode("IN").get());
		System.out.println(systemUnderTest.saveState(haryana));

		State delhi = State.of("Delhi", systemUnderTest.findCountryByCode("IN").get());
		System.out.println(systemUnderTest.saveState(delhi));

		State uttarPradesh = State.of("Uttar Pradesh", systemUnderTest.findCountryByCode("IN").get());
		System.out.println(systemUnderTest.saveState(uttarPradesh));

		State maharashtra = State.of("Maharashtra", systemUnderTest.findCountryByCode("IN").get());
		System.out.println(systemUnderTest.saveState(maharashtra));

		State andhraPradesh = State.of("Andhra Pradesh", systemUnderTest.findCountryByCode("IN").get());
		System.out.println(systemUnderTest.saveState(andhraPradesh));

		State california = State.of("California", systemUnderTest.findCountryByCode("US").get());
		System.out.println(systemUnderTest.saveState(california));

		State newYork = State.of("New York", systemUnderTest.findCountryByCode("US").get());
		System.out.println(systemUnderTest.saveState(newYork));

		State illinois = State.of("Illinois", systemUnderTest.findCountryByCode("US").get());
		System.out.println(systemUnderTest.saveState(illinois));

		State london = State.of("London", systemUnderTest.findCountryByCode("GB").get());
		System.out.println(systemUnderTest.saveState(london));
	}

	@Test
	public void test05FindStateById() {
	}

	@Test
	public void test06FindStateByName() {
		Optional<State> state = systemUnderTest.findStateByName("Haryana");
		assertTrue(state.isPresent() && state.get().name().equals("Haryana"));
		System.out.println("state --->>>" + state);
	}

	@Test
	public void test07FindAllStates() {
		List<State> states = systemUnderTest.findAllStates();
		assertTrue(states.size() == 9);
		System.out.println("states --->>" + states);
	}

	@Test
	public void test07FindAllStatesByCountryCode() {
		List<State> states = systemUnderTest.findAllStatesByCountryCode("IN");
		assertTrue(states.size() == 5);
	}

	@Test
	public void test08SaveCity() {
		City gurgaon = City.of("Gurgaon", systemUnderTest.findStateByName("Haryana").get());
		System.out.println(systemUnderTest.saveCity(gurgaon));

		City delhi = City.of("Delhi", systemUnderTest.findStateByName("Delhi").get());
		System.out.println(systemUnderTest.saveCity(delhi));

		City noida = City.of("Noida", systemUnderTest.findStateByName("Uttar Pradesh").get());
		System.out.println(systemUnderTest.saveCity(noida));

		City hyderabad = City.of("Hyderabad", systemUnderTest.findStateByName("Andhra Pradesh").get());
		System.out.println(systemUnderTest.saveCity(hyderabad));

		City pune = City.of("Pune", systemUnderTest.findStateByName("Maharashtra").get());
		System.out.println(systemUnderTest.saveCity(pune));

		City mumbai = City.of("Mumbai", systemUnderTest.findStateByName("Maharashtra").get());
		System.out.println(systemUnderTest.saveCity(mumbai));

		City milpitas = City.of("Milpitas", systemUnderTest.findStateByName("California").get());
		System.out.println(systemUnderTest.saveCity(milpitas));

		City sanFrancisco = City.of("San Francisco", systemUnderTest.findStateByName("California").get());
		System.out.println(systemUnderTest.saveCity(sanFrancisco));

		City newYork = City.of("New York", systemUnderTest.findStateByName("New York").get());
		System.out.println(systemUnderTest.saveCity(newYork));

		City chicago = City.of("Chicago", systemUnderTest.findStateByName("Illinois").get());
		System.out.println(systemUnderTest.saveCity(chicago));

		City london = City.of("London", systemUnderTest.findStateByName("London").get());
		System.out.println(systemUnderTest.saveCity(london));
	}

	@Test
	public void test09FindCityById() {
	}

	@Test
	public void test10FindAllCities() {
		List<City> cities = systemUnderTest.findAllCities();
		System.out.println("cities --->>" + cities);
		assertTrue(cities.size() == 11);
	}

	@Test
	public void testFindAllCitiesByStateId() {
		List<City> cities = systemUnderTest.findAllCitiesByStateId(1);
		assertTrue(cities.size() == 1);
	}

	@Test
	public void testFindAllCitiesByStateName() {
		List<City> cities = systemUnderTest.findAllCitiesByStateName("Haryana");
		assertTrue(cities.size() == 1);
	}

	@Test
	public void testFindAllCitiesByCountryCode() {
		List<City> cities = systemUnderTest.findAllCitiesByCountryCode("IN");
		assertTrue(cities.size() == 6);
	}
}
