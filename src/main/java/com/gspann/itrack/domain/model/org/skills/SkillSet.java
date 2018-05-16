package com.gspann.itrack.domain.model.org.skills;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.gspann.itrack.domain.model.common.type.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
@ToString
//@Entity
//@Table(name = "SKILL_SET")
public class SkillSet extends AbstractIdentifiable<Integer> {
	
	@ElementCollection
	@CollectionTable(
		name = "TECHNOLOGY",
		joinColumns = @JoinColumn(name = "ID"))
	@Column(name = "NAME")
	private Set<String> technologies = new HashSet<String>();
    
}
