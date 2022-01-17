package ssf.weather.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import ssf.weather.service.WeatherService;

@RestController
@RequestMapping(path = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherRestController {

    @Autowired
    WeatherService weatherSvc;

    // private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @GetMapping
    public ResponseEntity<String> getWeatherByRequestParam(@RequestParam(defaultValue = "Singapore") String city) {
        return getWeather(city);
    }

    @GetMapping(value = "/{city}")
    public ResponseEntity<String> getWeatherByPathVariable(@PathVariable String city) {
        return getWeather(city);
    }

    private ResponseEntity<String> getWeather(String city) {
        if (city.equals(null) || city.length() == 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            Json.createObjectBuilder()
                                    .add("Error", "City Required.")
                                    .build()
                                    .toString());
        } else {
            return ResponseEntity
                    .ok()
                    .body(weatherSvc.getWeather(city).toString());
        }
    }
}