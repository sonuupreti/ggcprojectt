package com.gspann.itrack.domain.model.business;

import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.gspann.itrack.domain.common.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
//@Entity
//@Table(name = "REBATES")
public class Rebate extends AbstractIdentifiable<Rebate, Integer> {

	private Year year;
	
	private float rebate;
}
