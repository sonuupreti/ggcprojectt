package com.gspann.itrack.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gspann.itrack.domain.model.business.Account;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AccountRepository extends JpaRepository<Account, String> {
}
