package com.gspann.itrack.domain.model.org.structure;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;
import com.gspann.itrack.domain.common.type.AbstractAssignable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@Entity
@Table(name = "ENGAGEMENT_STATUSES", uniqueConstraints = @UniqueConstraint(name = UNQ_ENG_STATUS_DESCRIPTION, columnNames = { "DESCRIPTION"}))
@Immutable
public class EngagementStatus extends AbstractAssignable<String> {

	// Active, Did Not Join, Terminated, On Long leave, On Bench, Pending, Resigned, Absconding

	@NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 50)
    private String description;
	
    public static EngagementStatus of(String code, String description) {
    	EngagementStatus engagementStatus = new EngagementStatus();
    	engagementStatus.code = code;
    	engagementStatus.description = description;
    	return engagementStatus;
    }
    
    public static EngagementStatus byStatusCode(CODE statusCode) {
    	return of(statusCode.name(), statusCode.description);
    }
	
	public enum CODE implements StringValuedEnum {
		
		// ACTIVE, DID_NOT_JOIN, TERMINATED, ON_LONG_LEAVE, ON_BENCH, PENDING, RESIGNED, ABSCONDING
		
		//@formatter:off 
		PENDING("Joining Awaited"), 
		ACTIVE("Active On Project"), 
		DID_NOT_JOIN("Did Not Join"), 
		TERMINATED("Terminated"), 
		ON_LONG_LEAVE("On Long Leave"), 
		ON_BENCH("On Bench"),
		RESIGNED("Resigned"), 
		ABSCONDED("Absconded");
		//@formatter:on
		
		private final String description;

		private CODE(final String description) {
			this.description = description;
		}

		@Override
		public String value() {
			return this.description;
		}

		@Override
		public StringValuedEnum enumByValue(String statusCode) {
			for (CODE e : values())
				if (e.value().equals(statusCode))
					return e;
			throw new IllegalArgumentException();
		}
	}
}
