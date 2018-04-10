package com.gspann.itrack.domain.model.timesheets;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.projects.Project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "TIME_SHEET_ENTRIES")
public class TimeSheetEntry extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "PROJECT_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_TIME_SHEET_ENTRIES_PROJECT_CODE))
	private Project project;

	@NotNull
	@Column(name = "HOURS", nullable = false, length = 5)
	public Duration hours;

	@Column(name = "DESCRIPTION", nullable = true, length = 255)
	private String description;

	@NotNull
	@ManyToOne
    @JoinColumn(name = "DAILY_TIME_SHEET_ID", updatable = false, insertable = false, foreignKey = @ForeignKey(name = FK_TIME_SHEET_ENTRIES_DAILY_TIME_SHEET_ID))
	private DailyTimeSheet dailyTimeSheet;

	@Column(name = "IS_APPROVED", length = 1)
	@org.hibernate.annotations.Type(type = "yes_no")
	@org.hibernate.annotations.ColumnDefault("'N'")
	private boolean isApproved = false;

}
