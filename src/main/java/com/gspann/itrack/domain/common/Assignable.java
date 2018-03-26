package com.gspann.itrack.domain.common;

import java.io.Serializable;

public interface Assignable<CodeType extends Serializable & Comparable<CodeType> & CharSequence> {

	public CodeType code();
}
