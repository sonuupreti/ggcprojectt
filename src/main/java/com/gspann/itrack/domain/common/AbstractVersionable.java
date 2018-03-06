/*
 * This is a common dao with basic CRUD operations and is not limited to any
 * persistent layer implementation
 *
 * Copyright (C) 2008  Imran M Yousuf (imyousuf@smartitengineering.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.gspann.itrack.domain.common;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 
 * 
 * @category: Domain
 * @version: 1.0.0-SNAPSHOT
 * 
 * @author: rajveer.singh
 * @created: 4/5/14 1:47 AM
 * @company: &copy; 2014, Ksoot Softwares
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@formatter:off
public abstract class AbstractVersionable<Template extends Identifiable<IdType>, 
        IdType extends Comparable<IdType> & Serializable, VersionType extends Comparable<VersionType> & Serializable>
        extends AbstractIdentifiable<Template, IdType> implements
        Versionable<VersionType> {
//@formatter:on

    @Version
    @Access(AccessType.FIELD)
    @Column(name = "version", nullable = false)
    private VersionType version;

    protected AbstractVersionable() {
    }

    @Override
    public VersionType getVersion() {
        return version;
    }
}