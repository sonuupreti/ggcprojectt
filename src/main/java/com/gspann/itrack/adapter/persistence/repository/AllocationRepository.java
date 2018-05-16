package com.gspann.itrack.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gspann.itrack.domain.model.allocations.Allocation;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {

}
