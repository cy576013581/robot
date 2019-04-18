package com.cy.robot;

import com.cy.robot.config.ApplicationConstants;
import com.cy.robot.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    private final Environment env;

    public Application(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
//    StandardTokenizer.SEGMENT.enableCustomDictionaryForcing(true);
//    SpringApplication.run(Application.class, args);
        SpringApplication app = new SpringApplication(Application.class);
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
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "External: \t{}://{}:{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("server.port"),
                protocol,
                hostAddress,
                env.getProperty("server.port"),
                env.getActiveProfiles());
    }

    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles
                .contains(ApplicationConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles
                .contains(ApplicationConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                    "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
