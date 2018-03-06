package com.gspann.itrack.common.enums;


/**
 * Enums with an associated string value
 * 
 */
public interface BaseEnum<E extends BaseEnum<E, V>, V> extends Comparable<E> {
	
	public V value();
}
