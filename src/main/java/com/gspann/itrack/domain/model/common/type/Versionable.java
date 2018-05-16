package com.gspann.itrack.domain.model.common.type;

import java.io.Serializable;

public interface Versionable<VersionType extends Comparable<VersionType> & Serializable> {

	public VersionType version();
}
