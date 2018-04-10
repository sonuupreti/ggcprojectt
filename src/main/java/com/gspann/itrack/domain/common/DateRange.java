package com.gspann.itrack.domain.common;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode
@Embeddable
public class DateRange {

	@NotNull
	@Column(name = "FROM_DATE", nullable = false)
	private LocalDate fromDate;

	@Column(name = "TILL_DATE", nullable = true)
	private LocalDate tillDate;
}
