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
    	return of(code.value(), code.description);
    }
    
    public static ProjectType fixBid() {
    	return of(CODE.FIX_BID.value(), CODE.FIX_BID.description);
    }

    public static ProjectType TnM() {
    	return of(CODE.TnM.value(), CODE.TnM.description);
    }

    public static ProjectType milestone() {
    	return of(CODE.MILESTONE.value(), CODE.MILESTONE.description);
    }

    public static ProjectType investment() {
    	return of(CODE.INVESTMENT.value(), CODE.INVESTMENT.description);
    }

    public static ProjectType bench() {
    	return of(CODE.BENCH.value(), CODE.BENCH.description);
    }

    public static ProjectType timeOff() {
    	return of(CODE.TIME_OFF.value(), CODE.TIME_OFF.description);
    }

	public enum CODE implements StringValuedEnum {
	
		// Fix Bid, T&M, Milestone, Investment, Bench, Time-off
	
		//@formatter:off
		FIX_BID("FBD", "Fix Bid"), 
		TnM("TnM", "Time and Materials"), 
		MILESTONE("MIL", "Milestone"), 
		INVESTMENT("INV", "Investment"), 
		BENCH("BNCH", "Bench"), 
		TIME_OFF("TMO", "Time-Off");
		//@formatter:on

		private final String value;
		private final String description;

		private CODE(final String value, final String description) {
			this.value = value;
			this.description = description;
		}

		@Override
		public String value() {
			return this.value;
		}


		public String description() {
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
