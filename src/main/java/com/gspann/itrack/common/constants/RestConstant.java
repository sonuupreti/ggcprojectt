package com.gspann.itrack.common.constants;

import lombok.Value;

@Value
public class RestConstant {
	
	public static final String SLASH = "/";

	public static final String DOCS_DIR = SLASH + "docs";

	public static final String API = SLASH + "api";
	
    public static final String VERSION_v1 = "v1";

    public static final String VERSION_v2 = "v2";

    public static final String CURRENT_VERSION = VERSION_v1;

    public static final String ITRACK_API = API + SLASH + CURRENT_VERSION;
    
    public static final String PATH_VARIABLE_ID = "{id}";
}
