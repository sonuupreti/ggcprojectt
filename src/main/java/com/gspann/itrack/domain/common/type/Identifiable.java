package com.gspann.itrack.domain.common.type;

import java.io.Serializable;

public interface Identifiable<IdType extends Number & Comparable<IdType> & Serializable> {

	public IdType id();
}
