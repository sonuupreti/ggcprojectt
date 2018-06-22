package com.gspann.itrack.common.constants;

import java.time.ZoneId;
import java.util.Locale;

import lombok.Value;

/**
 * Application constants.
 */
@Value
public final class ApplicationConstant {

    private ApplicationConstant() {
    }

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    public static final String ID_GENERATOR = "ID_GENERATOR";

    public static final String ID_GENERATOR_POOLED = "ID_GENERATOR_POOLED";

    public static final String CONFIG_BASE_NAME_APPLICATION = "application";

    public static final String CONFIG_BASE_NAME_PERSISTENCE = "persistence";

    public static final String CONFIG_ROOT = "classpath:config/";
    public static final String YML = ".yml";

    // public static final String CONFIG_BASE_NAMES = CONFIG_BASE_NAME_APPLICATION + ", " + CONFIG_BASE_NAME_PERSISTENCE;
    public static final String CONFIG_DEFAULT = CONFIG_ROOT + CONFIG_BASE_NAME_APPLICATION + YML + ", " + CONFIG_ROOT + CONFIG_BASE_NAME_PERSISTENCE + YML;
    public static final String CONFIG_DEV = CONFIG_ROOT + CONFIG_BASE_NAME_APPLICATION + "-" + SPRING_PROFILE_DEVELOPMENT + YML + ", " + CONFIG_ROOT + CONFIG_BASE_NAME_PERSISTENCE + "-" + SPRING_PROFILE_DEVELOPMENT + YML;
    public static final String CONFIG_PROD = CONFIG_ROOT + CONFIG_BASE_NAME_APPLICATION + "-" + SPRING_PROFILE_PRODUCTION +  YML + ", " + CONFIG_ROOT + CONFIG_BASE_NAME_PERSISTENCE + "-" + SPRING_PROFILE_PRODUCTION +  YML;

    public static final String SPRING_CONFIG_NAME = "spring.config.name";

    public static final String SPRING_CONFIG_LOCATION = "spring.config.location";

    public static void setSystemProperites() {
        System.out.println("CONFIG_DEFAULT --->>" + CONFIG_DEFAULT);
        System.out.println("CONFIG_DEV --->>" + CONFIG_DEV);
        System.out.println("CONFIG_PROD --->>" + CONFIG_PROD);
        System.setProperty(SPRING_CONFIG_NAME, CONFIG_BASE_NAME_PERSISTENCE);
//        System.setProperty(SPRING_CONFIG_LOCATION, CONFIG_ROOT);
//        System.setProperty(SPRING_CONFIG_LOCATION, CONFIG_DEFAULT);
        System.out.println("Configuration files --->>" +  System.getProperty(SPRING_CONFIG_LOCATION));
    }
    
    public final static ZoneId DEFAULT_TIME_ZONE = ZoneId.systemDefault();
    public final static Locale DEFAULT_LOCALE = Locale.getDefault();
    
}
