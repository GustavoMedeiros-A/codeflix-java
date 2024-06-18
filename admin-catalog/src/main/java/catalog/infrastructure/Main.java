package catalog.infrastructure;

import org.springframework.boot.SpringApplication;
import catalog.infrastructure.configuration.WebServerConfig;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(WebServerConfig.class, args);
    }
}
