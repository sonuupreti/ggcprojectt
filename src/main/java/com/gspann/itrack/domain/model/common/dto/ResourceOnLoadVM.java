package com.gspann.itrack.domain.model.common.dto;

import java.util.List;
import java.util.Set;

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
    // List<Pair<Short, String>> companiesList;
    List<Pair<Integer, String>> locationsList;
    // List<Pair<Short, String>> departmentList;
    // List<Pair<Short, String>> designationList;
    List<Pair<Integer, String>> technologiesPairs;
    List<Pair<String, String>> currencyPairs;
    List<Pair<String, String>> practiceList;

    private Set<CompanyDTO> companyDTOSet;

}
