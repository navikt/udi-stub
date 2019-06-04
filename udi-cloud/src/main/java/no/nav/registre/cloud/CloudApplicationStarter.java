package no.nav.registre.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"no.nav.registre.core"})
public class CloudApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(CloudApplicationStarter.class, args);
    }

}
