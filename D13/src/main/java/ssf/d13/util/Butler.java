package ssf.d13.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;

public class Butler {

    private static final String OPTION_DATA_DIR = "dataDir";
    private static final String LOG_DELINEATOR = " >>>>> ";
    private static final List<Path> DOC_ROOT = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(Butler.class);

    public static void log(String subject, String action) {
        logger.info(subject + LOG_DELINEATOR + action);
    }

    public static void log(String msg) {
        logger.info(msg);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static boolean checkDataDir(String[] args) {
        boolean dataDirExists = false;
        DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
        
        for (String arg : args) {
            log("Arg", arg);
        }

        if (appArgs.containsOption(OPTION_DATA_DIR)) {

            List<String> dataDir = appArgs.getOptionValues(OPTION_DATA_DIR);
            dataDir.forEach(string -> {
                log("Path", string);
            });

            for (String p : dataDir) {
                Path path = Paths.get(p);
                File file = path.toFile();
                dataDirExists = file.isDirectory();

                if (dataDirExists) {
                    log(file.toString(), "exists.");
                } else {
                    log(file.toString(), "not found.");
                    log("Attempting to create missing folders...");
                    dataDirExists = file.mkdirs();

                    if (dataDirExists) {
                        log(file.toString(), "created.");
                    } else {
                        log(file.toString(), "failed to create.");
                        break;
                    }

                }

                if (dataDirExists) {
                    DOC_ROOT.add(path);
                }

            }
        } else {
            warn("Data directory option not found.");
        }
        return dataDirExists;
    }
}
