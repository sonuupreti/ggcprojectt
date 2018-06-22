package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import com.gspann.itrack.domain.model.allocations.ResourceProjectAllocationProjection;
import com.gspann.itrack.domain.model.timesheets.Week;

public interface AllocationRepositoryJPA {

    public List<ResourceProjectAllocationProjection> findAllAllocationSummaries(final String resourceCode, final Week week);
}
