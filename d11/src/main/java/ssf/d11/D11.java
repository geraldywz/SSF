package ssf.d11;

import java.util.Collections;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class D11 {

	public static void main(String[] args) {
		// SpringApplication.run(D11.class, args);

		SpringApplication app = new SpringApplication(D11.class);

		String port = "8080";
		ApplicationArguments cliOpts = new DefaultApplicationArguments(args);
		if (cliOpts.containsOption("port")) {
			port = cliOpts.getOptionValues("port").get(0);
		}
		app.setDefaultProperties(Collections.singletonMap("server.port", port));
		// System.out.printf("Application started on port %d\n", port);
		app.run(args);
	}

}
