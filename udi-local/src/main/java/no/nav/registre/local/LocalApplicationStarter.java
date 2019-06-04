package no.nav.registre.local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"no.nav.registre.core"})
public class LocalApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(LocalApplicationStarter.class, args);
    }
}
