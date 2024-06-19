package catalog.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.env.AbstractEnvironment;

import catalog.infrastructure.configuration.WebServerConfig;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Main {
    public static void main(String[] args) {
        System.out.println("Print");
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
        SpringApplication.run(WebServerConfig.class, args);
    }
}
