package otus.nchuraeva.spring.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceDiscoveryServerMain {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryServerMain.class, args);
    }
}
