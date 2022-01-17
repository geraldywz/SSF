package ssf.marvel.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ssf.marvel.util.Constants.*;

public class ArgsParser {

    public static final String ARGS_PORT = "PORT";
    public static final String ARGS_PUBLIC = "PUBLIC";
    public static final String ARGS_PRIVATE = "PRIVATE";

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
                    case "public":
                        if (optionValues != null && optionValues.get(0) != null) {
                            KEY_PUBLICKEY = optionValues.get(0);
                            logger.info("Marvel Public Key >>>>> " + KEY_PUBLICKEY);
                            objBuilder.add(ARGS_PUBLIC, KEY_PUBLICKEY);
                        }
                        break;
                    case "private":
                        if (optionValues != null && optionValues.get(0) != null) {
                            KEY_PRIVATEKEY = optionValues.get(0);
                            logger.info("Marvel Public Key >>>>> " + KEY_PRIVATEKEY);
                            objBuilder.add(ARGS_PRIVATE, KEY_PRIVATEKEY);
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