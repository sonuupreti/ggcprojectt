package com.gspann.itrack.domain.model.business;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
@Table(name = "REBATES")
public class Rebate extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@ManyToOne
    @JoinColumn(name = "ACCOUNT_CODE", updatable = false, insertable = false, foreignKey = @ForeignKey(name = FK_REBATES_ACCOUNT_CODE))
	@Setter
	private Account account;

	@Column(name = "YEAR", nullable = false)
	private Year year;

	@Column(name = "PERCENT", nullable = false, columnDefinition = "Decimal(10,2) check (REBATE>=0.00 AND REBATE<=100.00) default '0.00'")
	private float percent;
	
	public static RebateYearBuilder of(final float percent) {
		return new RebateBuilder(percent);
	}
	
	public interface RebateYearBuilder {
		public Rebate forYear(final Year year);
	}
	
	public static class RebateBuilder implements RebateYearBuilder {
		private Rebate rebate;
		
		RebateBuilder(final float percent) {
			this.rebate = new Rebate();
			rebate.percent = percent;
		}

		@Override
		public Rebate forYear(Year year) {
			this.rebate.year = year;
			return this.rebate;
		}
	}
}
