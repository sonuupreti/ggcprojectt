package com.gspann.itrack.domain.model.common.type;

import java.io.Serializable;

public interface Identifiable<IdType extends Number & Comparable<IdType> & Serializable> {

	public IdType id();
}
