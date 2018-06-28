package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.common.dto.ResourceSearchDTO;
import com.gspann.itrack.domain.model.staff.Resource;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ResourceRepository extends JpaRepository<Resource, String> {

    @Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(r.code, r.name) FROM Resource r where r.code = :code")
    Pair<String, String> findCodeAndName(@Param("code") String code);

    @Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(r.code, r.name) FROM Resource r")
    List<Pair<String, String>> findAllCodeAndName();

    @Query("SELECT new com.gspann.itrack.domain.model.common.dto.ResourceSearchDTO(r.code,r.emailId,r.name,r.designation.name)  FROM Resource r where UPPER(r.code) like :name or UPPER(r.name) like :name "
            + "or UPPER(r.emailId) like :name")
    List<ResourceSearchDTO> searchResourceByParameter(@Param("name") String searchParam);

}
