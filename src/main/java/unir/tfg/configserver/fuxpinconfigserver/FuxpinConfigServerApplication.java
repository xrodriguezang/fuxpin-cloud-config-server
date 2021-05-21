package unir.tfg.configserver.fuxpinconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@EnableConfigServer
@SpringBootApplication
public class FuxpinConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuxpinConfigServerApplication.class, args);
	}

}
