package ru.nsu.ccfit.dymova;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.nsu.ccfit.dymova.logic.LoadController;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private LoadController loadController;

    public static void main(String[] args) {
        log.info("start");
        SpringApplication.run(Application.class, args);
        log.info("end");
    }

    @Bean
    CommandLineRunner init() {
        return args -> loadController.loadToDatabase();
    }
}
