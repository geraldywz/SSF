package ssf.marvel.controller;

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
import ssf.marvel.service.MarvelService;

@RestController
@RequestMapping(path = "/marvel", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarvelRestController {

    @Autowired
    MarvelService friday;

    // private static final Logger logger =
    // LoggerFactory.getLogger(WeatherController.class);

    @GetMapping
    public ResponseEntity<String> getWeatherByRequestParam(@RequestParam(defaultValue = "Hulk") String character) {
        return getCharacter(character);
    }

    @GetMapping(value = "/{character}")
    public ResponseEntity<String> getWeatherByPathVariable(@PathVariable String character) {
        return getCharacter(character);
    }

    private ResponseEntity<String> getCharacter(String character) {
        if (character.equals(null) || character.length() == 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            Json.createObjectBuilder()
                                    .add("Error", "Hero Required.")
                                    .build()
                                    .toString());
        } else {
            return ResponseEntity
                    .ok()
                    .body(friday.getCharacter(character).toString());
        }
    }
}