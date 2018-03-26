package com.gspann.itrack.domain.model.staff;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Embeddable
public class Image {

    @NotNull
    @Column(nullable = false)
    protected String name;

    @NotNull
    @Column(nullable = false)
    protected String filename;

    @NotNull
    protected int sizeX;

    @NotNull
    protected int sizeY;

    public Image() {
    }

    public Image(String name, String filename, int sizeX, int sizeY) {
        this.name = name;
        this.filename = filename;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
}
