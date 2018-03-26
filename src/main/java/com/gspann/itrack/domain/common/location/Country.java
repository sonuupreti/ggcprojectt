package com.gspann.itrack.domain.common.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.domain.common.Assignable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent=true)
@NoArgsConstructor
@Entity
@Table(name = "LOCATION_COUNTRY", uniqueConstraints = 
	{
			@UniqueConstraint(name = "UNQ_CNTRY_CODE", columnNames = { "CODE" }),
			@UniqueConstraint(name = "UNQ_CNTRY_NAME", columnNames = { "NAME" })
	})
@Immutable
public class Country implements Assignable<String> {

	@Id
    @Column(name = "CODE", nullable = false, length = 2)
    protected String code;
	
    @NotNull
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

//    @NotNull
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch=FetchType.LAZY)
//    private Set<State> states = new HashSet<State>();
    
    public static Country of(String code, String name) {
    	Country country = new Country();
    	country.code = code;
    	country.name = name;
    	return country;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Country [code=");
        builder.append(code());
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
