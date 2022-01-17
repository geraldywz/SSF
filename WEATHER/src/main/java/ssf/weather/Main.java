package ssf.weather;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.json.JsonObject;
import ssf.weather.util.ArgsParser;

@SpringBootApplication
public class Main {

	private static final Logger logger = LoggerFactory.getLogger(ArgsParser.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Main.class);
		logger.info("ARGS Length >>>>> " + args.length);
		JsonObject argsObj = ArgsParser.parse(args);
		logger.info("ARGS Parsed >>>>> " + argsObj.toString());
		if (argsObj.containsKey(ArgsParser.ARGS_PORT)) {
			app.setDefaultProperties(
					Collections.singletonMap("server.port", argsObj.getString(ArgsParser.ARGS_PORT)));
		}
		app.run(args);
	}
}