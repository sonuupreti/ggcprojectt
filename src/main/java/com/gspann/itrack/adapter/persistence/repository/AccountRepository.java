package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.common.dto.Pair;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AccountRepository extends JpaRepository<Account, String> {
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(a.code, a.customerName) FROM Account a where a.code = :code") 
    Pair<String, String> findCodeAndName(@Param("code") String code);
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(a.code, a.customerName) FROM Account a") 
    List<Pair<String, String>> findAllCodeAndName();
}
