package com.gspann.itrack.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gspann.itrack.domain.model.staff.Resource;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ResourceRepository extends JpaRepository<Resource, String> {
}
