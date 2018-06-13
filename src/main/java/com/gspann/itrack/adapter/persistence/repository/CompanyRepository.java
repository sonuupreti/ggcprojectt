package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.org.structure.Company;

public interface CompanyRepository extends JpaRepository<Company, String> {
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(r.id, r.name) FROM Company r") 
    List<Pair<String, String>> findAllCodeAndName();
}
