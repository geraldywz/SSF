package ssf.d13;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static ssf.d13.util.Constants.*;

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
        boolean dataDirExist = false;
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
                dataDirExist = file.isDirectory();
                if (dataDirExist) {
                    logger.info(file.toString() + LOG_DELINEATOR + "exists.");
                } else {
                    logger.info(file.toString() + LOG_DELINEATOR + "not found.");
                    logger.info("Attempting to create missing folders...");
                    dataDirExist = file.mkdirs();
                    if (dataDirExist) {
                        logger.info(file.toString() + LOG_DELINEATOR + "created.");
                    } else {
                        logger.info(file.toString() + LOG_DELINEATOR + "failed to create.");
                        break;
                    }
                }
                if (dataDirExist) {
                    DOC_ROOT.add(path);
                    // springApp.setDefaultProperties(Collections.singletonMap("server.port",portNumber));
                }
            }
        }

        if (dataDirExist) {
            springApp.run(args);
        } else {
            logger.info("Terminating Process.");
            System.exit(1);
        }
    }
}
