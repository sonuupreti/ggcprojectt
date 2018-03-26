package com.gspann.itrack.adapter.persistence.idgenerator;

import com.gspann.itrack.common.enums.StringValuedEnum;

public enum GlobalIDGeneratorConstant  implements StringValuedEnum {
	
    NAME("global_seq_generator"),

    SEQUENCE_NAME("global_sequence");

    private final String value;
    
    private GlobalIDGeneratorConstant(final String value) {
        this.value = value;
    }

	@Override
    public String value() {
        return this.value;
    }

    @Override
    public StringValuedEnum enumByValue(final String value) {
        for (GlobalIDGeneratorConstant e : values())
            if (e.value().equals(value))
                return e;
        throw new IllegalArgumentException();
    }
}
