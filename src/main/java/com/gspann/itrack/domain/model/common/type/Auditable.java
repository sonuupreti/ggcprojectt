package com.gspann.itrack.domain.model.common.type;

import java.io.Serializable;

public interface Auditable<Author extends Comparable<Author> & Serializable, Moment extends Comparable<Moment> & Serializable>
		extends Serializable {

	public Author getCreatedBy();

	public void setCreatedBy(Author createdBy);

	public Moment getCreatedOn();

	public void setCreatedOn(Moment createdOn);

	public Author getLastModifiedBy();

	public void setLastModifiedBy(Author lastModifiedBy);

	public Moment getLastModifiedOn();

	public void setLastModifiedOn(Moment lastModifiedOn);
}
