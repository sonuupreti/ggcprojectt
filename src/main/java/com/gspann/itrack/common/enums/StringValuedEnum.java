package com.gspann.itrack.common.enums;

/**
 * Enums with an associated string value
 * 
 */
public interface StringValuedEnum {
	
    public String value();
    
    public StringValuedEnum enumByValue(final String value);
}
