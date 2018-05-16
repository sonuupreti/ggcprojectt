package com.gspann.itrack.domain.model.org.skills;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.type.AbstractIdentifiable;

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
//@Entity
//@Table(name = "TECHNOLOGY", uniqueConstraints = @UniqueConstraint(name = "UNQ_TECHNOLOGY_NAME", columnNames = { "NAME"}))
public class Technology extends AbstractIdentifiable<Short> {

    @NotNull
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

}
