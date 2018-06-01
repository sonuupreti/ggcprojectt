package com.example.itracktest;

import java.time.Duration;

import org.joda.time.LocalDate;

public class AdHocTest {

	public static void main(String[] args) {
		Duration duration = Duration.ofHours(4).plusMinutes(30).plusSeconds(50);
		
		System.out.println(LocalDate.now());

	}

}
