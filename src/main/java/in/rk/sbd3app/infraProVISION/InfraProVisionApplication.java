package in.rk.sbd3app.infraProVISION;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class InfraProVisionApplication {
	public static void main(String[] args) {
		SpringApplication.run(InfraProVisionApplication.class, args);
		log.info("Application started successfully.");
	}

}
