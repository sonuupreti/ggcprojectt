package com.gspann.itrack.domain.common;

import java.io.Serializable;

public interface Identifiable<IdType extends Comparable<IdType> & Serializable> {

	public IdType getId();
}
