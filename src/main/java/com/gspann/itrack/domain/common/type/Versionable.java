package com.gspann.itrack.domain.common.type;

import java.io.Serializable;

public interface Versionable<VersionType extends Comparable<VersionType> & Serializable> {

	public VersionType version();
}
