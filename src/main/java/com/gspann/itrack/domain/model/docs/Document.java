package com.gspann.itrack.domain.model.docs;

import java.time.ZonedDateTime;
import java.util.EnumSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.common.constants.ApplicationConstant;
import com.gspann.itrack.common.enums.StringValuedEnum;
import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
@Table(name = "DOCUMENTS")
public class Document extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@Column(nullable = false, length = 100)
	private String filename;

	// Commenting Not null, as there is a bug in JPA meta-model, Nut not null should be handled at application level
//	@NotNull 
	@Lob
	@Column(name = "DATA", nullable = false)
	private byte[] data;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false, length = 20)
	private TYPE type;

	@NotNull
	@Column(name = "UPLOADED_ON")
//	@Column(name = "UPLOADED_ON", insertable = false, updatable = false)
//	@org.hibernate.annotations.Generated(org.hibernate.annotations.GenerationTime.INSERT)
	private ZonedDateTime uploadedOn;

	public static Document of(final String filename, final byte[] data, final TYPE type, final ZonedDateTime uploadedOn) {
		Document doc = new Document();
		doc.filename = filename;
		doc.data = data;
		doc.type = type;
		doc.uploadedOn = uploadedOn;
		return doc;
	}

	public static Document ofProfileImage(final String filename, final byte[] data) {
		Document doc = new Document();
		doc.filename = filename;
		doc.data = data;
		doc.type = TYPE.PROFILE_IMAGE;
		doc.uploadedOn = ZonedDateTime.now(ApplicationConstant.DEFAULT_TIME_ZONE);
		return doc;
	}

	public enum TYPE {

		//@formatter:off
		PROFILE_IMAGE {
			public EnumSet<EXTENSION> allowedExtensions() {
				return imageExtensions;
			}
		},
		RESUME {
			public EnumSet<EXTENSION> allowedExtensions() {
				return docExtensions;
			}
		},
		TIMESHEET_SCREENSHOT {
			public EnumSet<EXTENSION> allowedExtensions() {
				return imageExtensions;
			}
		},
		PO {
			public EnumSet<EXTENSION> allowedExtensions() {
				return docExtensions;
			}
		},
		SOW {
			public EnumSet<EXTENSION> allowedExtensions() {
				return docExtensions;
			}
		};
		//@formatter:on

		private static EnumSet<EXTENSION> imageExtensions = EnumSet.of(EXTENSION.JPEG, EXTENSION.GIF, EXTENSION.PNG);

		private static EnumSet<EXTENSION> docExtensions = EnumSet.of(EXTENSION.DOC, EXTENSION.DOCX, EXTENSION.PDF);

		public abstract EnumSet<EXTENSION> allowedExtensions();
	}

	public enum EXTENSION implements StringValuedEnum {

		//@formatter:off
		JPEG("jpeg"),
		GIF("gif"),
		PNG("png"),
		DOC("doc"),
		DOCX("docx"),
		PDF("pdf");
		//@formatter:on

		private final String name;

		private EXTENSION(final String name) {
			this.name = name;
		}

		@Override
		public String value() {
			return this.name;
		}

		@Override
		public StringValuedEnum enumByValue(String type) {
			for (EXTENSION e : values())
				if (e.value().equals(type))
					return e;
			throw new IllegalArgumentException();
		}
	}
}
