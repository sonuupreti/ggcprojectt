package com.gspann.itrack.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.business.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
