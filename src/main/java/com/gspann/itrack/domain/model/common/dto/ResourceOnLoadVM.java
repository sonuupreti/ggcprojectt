package com.gspann.itrack.domain.model.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
public class ResourceOnLoadVM {
	List<Pair<Short, String>> companiesList;
	List<Pair<Integer, String>> locationsList;
	
}
