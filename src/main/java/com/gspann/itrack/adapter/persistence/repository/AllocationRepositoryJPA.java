package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import com.gspann.itrack.domain.model.common.dto.ResourceAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.ResourceProjectAllocationSummary;

public interface AllocationRepositoryJPA {

    public List<ResourceProjectAllocationSummary> findAllAllocationSummaries(final String resourceCode);
}
