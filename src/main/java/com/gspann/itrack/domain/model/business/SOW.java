package com.gspann.itrack.domain.model.business;

import java.time.LocalDate;
import java.util.List;

import com.gspann.itrack.domain.common.type.AbstractAutoAssignable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
//@Entity
//@Table(name = "SOW")
public class SOW extends AbstractAutoAssignable<String>  {

    private List<String> resources;
    
    private LocalDate startDate;

    private LocalDate endDate;
    
    private SOW parentSOW;
    
    private String supportingDocument;
}
