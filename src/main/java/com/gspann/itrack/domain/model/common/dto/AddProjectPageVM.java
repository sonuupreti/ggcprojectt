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
public class AddProjectPageVM {
	
	List<Pair<String, String>> resourceManagerList;
	
	List<Pair<Integer, String>> locationsList;
	
	List<Pair<String, String>> projectTypeList;
	
	List<Pair<String, String>> projectStatusList;
	
	List<Pair<String, String>> practiceList;
	List<Pair<String, String>>accountList;
	List<Pair<Integer, String>>technologyList;

}
