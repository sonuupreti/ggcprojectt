package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gspann.itrack.domain.model.allocations.Allocation;
import com.gspann.itrack.domain.model.common.dto.Pair;

public interface AllocationRepository extends JpaRepository<Allocation, Long>, AllocationRepositoryJPA {

	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(bs.code, bs.description) FROM BillabilityStatus bs") 
    public List<Pair<String, String>> findAllBillabilityStatusCodeAndDescription();
	
	public List<Allocation> findByResourceCode(String resourceCode);

	public List<Allocation> findByProjectCode(String resourceCode);
}
