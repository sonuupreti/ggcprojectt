package com.gspann.itrack.common.constants;

import lombok.Value;

@Value
public final class ValidationConstant {
	
	public static final class REGEX {
		public static final String ALPHANUMERIC = "";
		public static final String ALPHANUMERIC_WITH_DOT_AND_UNDERSCORE = "";
	}
	
	public static final class Resource {

		public static final String REGEX_LOGIN_ID = REGEX.ALPHANUMERIC_WITH_DOT_AND_UNDERSCORE;
		public static final String MESSAGE_LOGIN_ID_MANDATORY = "resource.loginId.mandatory";
		public static final String MESSAGE_LOGIN_ID_INVALID = "resource.loginId.invalid";
		
		public static final String MESSAGE_EMAIL_INVALID = "resource.emailId.invalid";

		public static final String REGEX_GREYT_HR_ID = "^[a-zA-Z0-9]+$";
		public static final String MESSAGE_GREYT_HR_ID_INVALID = "resource.greytHRId.invalid";

		public static final String REGEX_NAME = "";
		public static final String MESSAGE_NAME_INVALID = "resource.name.invalid";

		public static final String MESSAGE_COSTING_MANDATORY = "resource.costing.mandatory";

		// Resource must be allocated to TIME_OFF and 
		// at least one more project either bench or any other at any point of time
		public static final String MESSAGE_ALLOCATION_MANDATORY = "resource.allocation.mandatory";

		public static final String MESSAGE_PRIMARY_SKILLS_MANDATORY = "resource.primarySkills.mandatory";
		public static final String MESSAGE_SECONDARY_SKILLS_MANDATORY = "resource.secondarySkills.mandatory";
	}
	
}
