package com.gspann.itrack.domain.common.skill;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
@ToString
@Entity
@Table(name = "PRACTICES", uniqueConstraints = @UniqueConstraint(name = "UNQ_PRACTICE_NAME", columnNames = { "NAME"}))
public class Practice extends AbstractIdentifiable<Practice, Short> {

	// MOBILE APPS, SST, DevOps
	
    @NotNull
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @NotNull
    @Column(name = "LEAD", nullable = false, length = 100)
    private String lead;

    @NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description;
    
//    public static void main(String[] args) {
//    	ValidatorFactory factory = Validation
//    			.buildDefaultValidatorFactory();
//    	Validator validator = factory.getValidator();
//    	Practice Practice = new Practice();
//    	
//    	Set<ConstraintViolation<Practice>> constraintViolations =
//    			validator.validate( Practice );
//    	System.out.println(constraintViolations);
//	}
}
