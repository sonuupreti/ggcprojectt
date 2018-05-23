package com.gspann.itrack.domain.model.common.dto;



import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
//@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountDTO  {

	private String accountCode;
	
	@NotNull
    private String customerName;

    @NotNull
    private String customerEntity;
   
    @NotNull
    private String customerReportingManager;

    private Boolean customerTimeTracking;

    private String accountManagerCode;
    
    private String accountManagerName;
    
    private int cityId;

    private String location;

}
