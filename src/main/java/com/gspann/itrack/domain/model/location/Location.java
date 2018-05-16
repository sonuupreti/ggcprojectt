package com.gspann.itrack.domain.model.location;

import com.gspann.itrack.domain.model.common.dto.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor()
@AllArgsConstructor(staticName = "of")
@ToString()
public class Location {

	private int cityId;
	
	public static final LocationFormat DEFAULT_LOCATION_FORMAT = LocationFormat.CITY_STATE_COUNTRY_NAMES_HYPHEN_SEPERATED;

	private String countryName;
	
	private String stateName;
	
	private String cityName;
	
	public Location(final String countryName, final String stateName, String cityName) {
		this.countryName = countryName;
		this.stateName = stateName;
		this.cityName = cityName;
	}

	public String format() {
		return String.format(DEFAULT_LOCATION_FORMAT.value(), cityName, stateName, countryName);
	}
	
	public String format(final LocationFormat locationFormat) {
		return String.format(locationFormat.value(), cityName, stateName, countryName);
	}
	
	public Pair<Integer, String> listItem() {
		return new Pair<Integer, String>(this.cityId, format());
	}
	
	public Pair<Integer, String> listItem(final LocationFormat locationFormat) {
		return new Pair<Integer, String>(this.cityId, format(locationFormat));
	}
}
