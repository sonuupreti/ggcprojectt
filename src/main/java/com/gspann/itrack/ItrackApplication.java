package com.gspann.itrack;

import static com.gspann.itrack.common.constants.RestConstant.*;

import java.net.InetAddress;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.format.Formatter;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

import com.gspann.itrack.common.constants.ApplicationConstant;
import com.gspann.itrack.infra.config.ApplicationProperties;
import com.gspann.itrack.infra.config.DefaultProfileUtil;

import io.github.jhipster.config.JHipsterConstants;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
public class ItrackApplication {

	private static final Logger log = LoggerFactory.getLogger(ItrackApplication.class);

	private final Environment env;

	public ItrackApplication(Environment env) {
		this.env = env;
	}

	public static String CURIE_NAMESPACE = "itrack";

	@Bean
	public CurieProvider curieProvider() {
		return new DefaultCurieProvider(CURIE_NAMESPACE, new UriTemplate(DOCS_DIR + SLASH + "{rel}.html"));
	}

	/**
	 * Initializes itrack2.
	 * <p>
	 * Spring profiles can be configured with a program arguments
	 * --spring.profiles.active=your-active-profile
	 * <p>
	 * You can find more information on how profiles work with JHipster on <a href=
	 * "https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
	 */
	@PostConstruct
	public void initApplication() {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
				&& activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
			log.error("You have misconfigured your application! It should not run "
					+ "with both the 'dev' and 'prod' profiles at the same time.");
		}
		if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
				&& activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
			log.error("You have misconfigured your application! It should not "
					+ "run with both the 'dev' and 'cloud' profiles at the same time.");
		}

		// TODO: Confirm default timezone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Main method, used to run the application.
	 *
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ItrackApplication.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
						+ "External: \t{}://{}:{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol,
				hostAddress, env.getProperty("server.port"), env.getActiveProfiles());
		System.out.println("Default timezone --->>" + ApplicationConstant.DEFAULT_TIME_ZONE);
	}

	@Bean
	public Formatter<LocalDate> localDateFormatter() {
		return new Formatter<LocalDate>() {
			@Override
			public LocalDate parse(String text, Locale locale) throws ParseException {
				return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
			}

			@Override
			public String print(LocalDate object, Locale locale) {
				return DateTimeFormatter.ISO_DATE.format(object);
			}
		};
	}
}
