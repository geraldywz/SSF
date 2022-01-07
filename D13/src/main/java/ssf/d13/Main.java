package ssf.d13;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static ssf.d13.Constants.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        boolean dataDirExist = true;
        for (String arg : args) {
            logger.info("Arg" + LOG_DELINEATOR + arg);
        }
        SpringApplication springApp = new SpringApplication(Main.class);

        DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);

        if (appArgs.containsOption(OPTION_DATA_DIR)) {
            List<String> dataDir = appArgs.getOptionValues(OPTION_DATA_DIR);
            logger.info("Path" + LOG_DELINEATOR + dataDir);
            for (String p : dataDir) {
                Path path = Paths.get(p);
                File file = path.toFile();
                if (file.isDirectory()) {
                    logger.info(file.toString() + LOG_DELINEATOR + "exists.");
                } else if (file.mkdirs()) {
                    logger.info(file.toString() + LOG_DELINEATOR + "not found.");
                    logger.info("Attempting to create missing folders...");
                    logger.info(file.toString() + LOG_DELINEATOR + "created.");
                } else {
                    logger.info(file.toString() + LOG_DELINEATOR + "failed to create.");
                    dataDirExist = false;
                    break;
                }
                DOC_ROOT.add(path);
                // springApp.setDefaultProperties(Collections.singletonMap("server.port",portNumber));
            }
            if (dataDirExist) {
                springApp.run(args);
            }
        } else {
            logger.info("Terminating Process.");
            System.exit(0);
        }
    }
}
