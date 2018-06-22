package com.gspann.itrack.adapter.rest.timesheet;

import java.net.URISyntaxException;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResource;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// TODO: Not being used currently
//@Component
@RequiredArgsConstructor
public class TimeSheetResourceProcessor implements ResourceProcessor<Resource<TimeSheetResource>> {

	@NonNull
	private final TimeSheetLinks timesheetLinks;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.hateoas.ResourceProcessor#process(org.springframework.
	 * hateoas.ResourceSupport)
	 */
	@Override
	public Resource<TimeSheetResource> process(Resource<TimeSheetResource> resource) {

		TimeSheetResource timesheet = resource.getContent();
		try {
			resource.add(timesheetLinks.getLinks(timesheet));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return resource;
	}
}
