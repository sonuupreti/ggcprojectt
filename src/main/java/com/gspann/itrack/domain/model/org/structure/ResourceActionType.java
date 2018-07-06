package com.gspann.itrack.domain.model.org.structure;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gspann.itrack.common.enums.StringValuedEnum;

@Immutable
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResourceActionType implements StringValuedEnum {
    
    // @formatter:off
    JOIN("JOIN", "Resource joined"),
    DID_NOT_JOIN("DID_NOT_JOIN","Resource did not joined");
    // @formatter:on
    
    private String code;

    private String description;

    private ResourceActionType(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String value() {
        return this.description;
    }

    @Override
    public StringValuedEnum enumByValue(String value) {
        for (ResourceActionType e : values())
            if (e.value().equals(value))
                return e;
        throw new IllegalArgumentException();
    }

}
