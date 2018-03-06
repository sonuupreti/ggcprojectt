package com.gspann.itrack.domain.common;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractAuditable.class)
public abstract class AbstractAuditable_ {

	public static volatile SingularAttribute<AbstractAuditable, Instant> createdDate;
	public static volatile SingularAttribute<AbstractAuditable, String> createdBy;
	public static volatile SingularAttribute<AbstractAuditable, Instant> lastModifiedDate;
	public static volatile SingularAttribute<AbstractAuditable, String> lastModifiedBy;

}

