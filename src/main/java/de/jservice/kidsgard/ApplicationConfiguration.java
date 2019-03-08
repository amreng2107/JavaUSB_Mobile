package de.jservice.kidsgard;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "de.jservice.kidsgard")
@EnableJpaRepositories(basePackages = "de.jservice.kidsgard.integration")
public class ApplicationConfiguration {
}
