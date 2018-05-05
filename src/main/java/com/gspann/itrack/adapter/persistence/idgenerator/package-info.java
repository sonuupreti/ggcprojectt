//@formatter:off
@GenericGenerators({
	@GenericGenerator(
		name = GLOBAL_SEQ_ID_GENERATOR, 
		strategy = "enhanced-sequence", 
		parameters = {
			@Parameter(name = "sequence_name", value = GLOBAL_SEQ_NAME),
			@Parameter(name = "initial_value", value = GLOBAL_SEQ_INITIAL_VALUE),
			@Parameter(name = "increment_size", value = "1") 
		}
	),
	@GenericGenerator(
		name = POOLED_TABLE_GENERATOR, 
		strategy = "org.hibernate.id.enhanced.TableGenerator", 
		parameters = {
			@org.hibernate.annotations.Parameter(name = "value_column_name", value = "sequence_next_hi_value"),
	        @org.hibernate.annotations.Parameter(name = "prefer_entity_table_as_segment_value", value = "true"),
	        @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo"),
	        @org.hibernate.annotations.Parameter(name = "increment_size", value = "100")
		}
	),
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