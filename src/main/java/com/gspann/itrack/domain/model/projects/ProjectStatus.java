package com.gspann.itrack.domain.model.projects;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.common.enums.StringValuedEnum;
import com.gspann.itrack.domain.model.common.type.AbstractAssignable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@Entity
@Table(name = "PROJECT_STATUSES", uniqueConstraints = @UniqueConstraint(name = UNQ_PRJ_STATUS_DESCRIPTION, columnNames = { "DESCRIPTION"}))
@Immutable
public class ProjectStatus extends AbstractAssignable<String> {

	@NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;
	
	public boolean isPending() {
		return this.code().equals(CODE.PENDING.name());
	}
	
	public boolean isInProgress() {
		return this.code().equals(CODE.WIP.name());
	}
	
	public boolean isOnHold() {
		return this.code().equals(CODE.ON_HOLD.name());
	}
	
	public boolean isClosed() {
		return this.code().equals(CODE.CLOSED.name());
	}
	
    public static ProjectStatus of(String code, String description) {
    	ProjectStatus projectType = new ProjectStatus();
    	projectType.code = code;
    	projectType.description = description;
    	return projectType;
    }
	
    public static ProjectStatus byStatusCode(CODE code) {
    	return of(code.name(), code.description);
    }
    
    public static ProjectStatus pending() {
    	return byStatusCode(CODE.PENDING);
    }

    public static ProjectStatus inProgress() {
    	return byStatusCode(CODE.WIP);
    }

    public static ProjectStatus onHold() {
    	return byStatusCode(CODE.ON_HOLD);
    }

    public static ProjectStatus closed() {
    	return byStatusCode(CODE.CLOSED);
    }

	public enum CODE implements StringValuedEnum {

		// AWATING_START (Waiting to Start), WIP (Work in Progress), ON_HOLD (On Hold), CLOSED (Closed)
	
		//@formatter:off
		PENDING("Waiting to Start"), 
		WIP("Work in Progress"), 
		ON_HOLD("On Hold"), 
		CLOSED("Closed");
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
		public StringValuedEnum enumByValue(String type) {
			for (CODE e : values())
				if (e.value().equals(type))
					return e;
			throw new IllegalArgumentException();
		}
	}
}
