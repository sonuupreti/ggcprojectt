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

import com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "REBATES")
public class Rebate extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@ManyToOne
    @JoinColumn(name = "ACCOUNT_CODE", updatable = false, insertable = false, foreignKey = @ForeignKey(name = FK_REBATES_ACCOUNT_CODE))
	protected Account account;

	@Column(name = "YEAR", nullable = false)
	private Year year;

	@Column(name = "REBATE", nullable = false, columnDefinition = "Decimal(10,2) check (REBATE>=0.00 AND REBATE<=100.00) default '0.00'")
	private float rebate;
}
