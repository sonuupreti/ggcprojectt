package com.gspann.itrack.common.annotation.ddd;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An object that carries data between processes.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Data_transfer_object">Data
 *      Transfer Object pattern</a>
 */
@Retention(RetentionPolicy.SOURCE)
@Inherited
@Documented
public @interface DataTransferObject {

}
