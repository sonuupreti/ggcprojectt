package com.gspann.itrack.domain.model.staff;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "code" })
@ToString
public class ResourceIdentity {

	private String code;

	private String name;

	private byte[] image;

	public ResourceIdentity of(final String resourceCode, final String resourceName, final byte[] resourceImage) {
		return new ResourceIdentity(resourceCode, resourceName, resourceImage);
	}
}
