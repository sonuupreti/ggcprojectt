package com.gspann.itrack.common.enums;

/**
 * Enums with an associated string value
 * 
 */
public interface StringValuedEnum {
	
    public String getValue();
    
    public StringValuedEnum getByValue(final String _value);
}
