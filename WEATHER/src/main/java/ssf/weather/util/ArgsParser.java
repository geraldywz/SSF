package ssf.weather.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ssf.weather.util.Constants.*;

public class ArgsParser {

    public static final String ARGS_PORT = "PORT";
    public static final String ARGS_OWM = "OWM";
    public static final String ARGS_REDIS = "REDIS";

    private static final Logger logger = LoggerFactory.getLogger(ArgsParser.class);

    public static JsonObject parse(String[] args) {
        ApplicationArguments appArgs = new DefaultApplicationArguments(args);
        Set<String> options = appArgs.getOptionNames();
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        if (options != null && !options.isEmpty()) {
            for (String o : options) {
                List<String> optionValues = appArgs.getOptionValues(o);

                switch (o) {
                    case "port":
                        String portNumber = null;

                        if (optionValues == null || optionValues.get(0) == null) {
                            portNumber = System.getenv("PORT");

                            if (portNumber == null)
                                portNumber = DEFAULT_PORT;
                        } else {
                            portNumber = (String) optionValues.get(0);
                        }
                        logger.info("Port >>>>> " + portNumber);
                        objBuilder.add(ARGS_PORT, portNumber);
                        break;
                    case "owm":
                        if (optionValues != null && optionValues.get(0) != null) {
                            KEY_OPENWEATHERMAP = optionValues.get(0);
                            logger.info("OWM API Key >>>>> " + KEY_OPENWEATHERMAP);
                            objBuilder.add(ARGS_OWM, KEY_OPENWEATHERMAP);
                        }
                        break;
                    case "redis":
                        if (optionValues != null && optionValues.get(0) != null) {
                            KEY_REDISPASSWORD = optionValues.get(0);
                            logger.info("Redis Pwd >>>>> " + KEY_REDISPASSWORD);
                            objBuilder.add(ARGS_REDIS, KEY_REDISPASSWORD);
                        }
                        break;
                    default:

                        break;
                }
            }
        } else {
            objBuilder.add("Empty", "No Args found.");
        }
        return objBuilder.build();
    }
}