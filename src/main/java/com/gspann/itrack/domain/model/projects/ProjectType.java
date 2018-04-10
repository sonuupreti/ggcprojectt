package com.gspann.itrack.domain.model.projects;

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
@Table(name = "PROJECT_TYPES", uniqueConstraints = @UniqueConstraint(name = UNQ_PRJ_TYPE_DESCRIPTION, columnNames = { "DESCRIPTION"}))
@Immutable
public class ProjectType extends AbstractAssignable<String> {

	// Fix Bid, Time and Materials, Milestone, Investment, Bench, Time-Off

	@NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;
	
    public static ProjectType of(String code, String description) {
    	ProjectType projectType = new ProjectType();
    	projectType.code = code;
    	projectType.description = description;
    	return projectType;
    }
	
    public static ProjectType byTypeCode(CODE code) {
    	return of(code.name(), code.description);
    }
    
    public static ProjectType ofFixBid() {
    	return of(CODE.FIX_BID.name(), CODE.FIX_BID.description);
    }

    public static ProjectType ofTnM() {
    	return of(CODE.TnM.name(), CODE.TnM.description);
    }

    public static ProjectType ofMilestone() {
    	return of(CODE.MILESTONE.name(), CODE.MILESTONE.description);
    }

    public static ProjectType ofInvestment() {
    	return of(CODE.INVESTMENT.name(), CODE.INVESTMENT.description);
    }

    public static ProjectType ofBench() {
    	return of(CODE.BENCH.name(), CODE.BENCH.description);
    }

    public static ProjectType ofTimeOff() {
    	return of(CODE.TIME_OFF.name(), CODE.TIME_OFF.description);
    }

	public enum CODE implements StringValuedEnum {
	
		// Fix Bid, T&M, Milestone, Investment, Bench, Time-off
	
		//@formatter:off
		FIX_BID("Fix Bid"), 
		TnM("Time and Materials"), 
		MILESTONE("Milestone"), 
		INVESTMENT("Investment"), 
		BENCH("Bench"), 
		TIME_OFF("Time-Off");
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
