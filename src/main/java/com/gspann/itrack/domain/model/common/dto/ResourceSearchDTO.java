/**
 * 
 */
package com.gspann.itrack.domain.model.common.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author suresh.babu
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ResourceSearchDTO implements Serializable{
	
	private String resourceCode;
	
	private String emailId;
	
	private String name;
	
	private String designation;


}
