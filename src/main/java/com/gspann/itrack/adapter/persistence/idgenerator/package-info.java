//@formatter:off
@GenericGenerators({
		@GenericGenerator(
				name = GLOBAL_SEQ_ID_GENERATOR, 
				strategy = "enhanced-sequence", 
				parameters = {
						@Parameter(name = "sequence_name", value = GLOBAL_SEQ_NAME),
						@Parameter(name = "initial_value", value = GLOBAL_SEQ_INITIAL_VALUE),
						@Parameter(name = "increment_size", value = "1") 
				}),
		@GenericGenerator(
				name = PREFIXED_SEQUENTIAL_CODE_GENERATOR, 
				strategy = "com.gspann.itrack.adapter.persistence.idgenerator.PrefixedSequentialCodeGenerator"
		) 
})
//@formatter:on

package com.gspann.itrack.adapter.persistence.idgenerator;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.Hibernate.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;
import org.hibernate.annotations.Parameter;