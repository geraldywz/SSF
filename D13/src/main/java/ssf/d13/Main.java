package ssf.d13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ssf.d13.util.Butler;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication springApp = new SpringApplication(Main.class);        

        if (Butler.checkDataDir(args)) {
            springApp.run(args);
        } else {
            Butler.warn("Terminating Process.");
            System.exit(1);
        }
    }
}
