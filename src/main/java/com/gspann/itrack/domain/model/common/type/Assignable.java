package com.gspann.itrack.domain.model.common.type;

import java.io.Serializable;

public interface Assignable<CodeType extends Serializable & Comparable<CodeType>> {

	public CodeType code();

}
