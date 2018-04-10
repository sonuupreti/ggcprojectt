package com.gspann.itrack.domain.model.org.holidays;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class HolidayLocationId implements Serializable {

	private static final long serialVersionUID = 3330171875132788146L;

	@NotNull
	@Column(name = "HOLIDAY_ID")
	private Integer holidayId;

	@NotNull
	@Column(name = "LOCATION_ID")
	private Integer locationId;
}
