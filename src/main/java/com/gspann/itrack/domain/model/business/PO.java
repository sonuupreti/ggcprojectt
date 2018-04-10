package com.gspann.itrack.domain.model.business;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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
//@Table(name = "PO")
public class PO extends AbstractAutoAssignable<String>  {

    private String openingBalance;
    
    private String availableBalance;
    
    private LocalDate startDate;

    private LocalDate endDate;
    
    private SOW parentPO;
    
    private String supportingDocument;
}
