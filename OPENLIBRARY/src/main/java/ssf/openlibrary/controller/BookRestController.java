package ssf.openlibrary.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import ssf.openlibrary.service.BookService;

import static ssf.openlibrary.util.Constants.*;

@RestController
@RequestMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookRestController {

    @Autowired
    @Qualifier(BEAN_LIBRARY_SERVICE)
    BookService bookSvc;

    // private static final Logger logger =
    // LoggerFactory.getLogger(WeatherController.class);

    @GetMapping
    public ResponseEntity<String> getWeatherByRequestParam(String title) {
        return getWeather(title);
    }

    @GetMapping(value = "/{title}")
    public ResponseEntity<String> getWeatherByPathVariable(@PathVariable String title) {
        return getWeather(title);
    }

    private ResponseEntity<String> getWeather(String title) {
        if (title.equals(null) || title.length() == 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            Json.createObjectBuilder()
                                    .add("Error", "Title Required.")
                                    .build()
                                    .toString());
        } else {
            return ResponseEntity
                    .ok()
                    .body(bookSvc.search(title).toString());
        }
    }
}