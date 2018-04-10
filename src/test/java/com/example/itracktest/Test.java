package com.example.itracktest;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.Fraction;

public class Test {
	
	private static final String pattern = "/^[a-zA-Z0-9_]+([-.][a-zA-Z0-9_]+)*$/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		System.out.println(Pattern.matches(pattern, "rajveer.singh"));
		
		
	}

}
