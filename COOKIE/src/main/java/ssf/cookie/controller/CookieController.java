package ssf.cookie.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import ssf.cookie.service.FortuneCookie;

/*Sample REST*/

@RestController
@RequestMapping(path = "/cookies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CookieController {

    @Autowired
    FortuneCookie service;

    @GetMapping
    public ResponseEntity<String> getCookies(@RequestParam(defaultValue = "1") Integer count) {
        ResponseEntity<String> responseEntity = null;

        if (count < 1 || count > 10) {
            JsonObject error = Json.createObjectBuilder()
                    .add("error", "Count must be inclusive of (1 - 10) inclusive.")
                    .build();

            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error.toString());
        } else {
            List<String> cookies = service.getCookies(count);

            JsonArrayBuilder cookieBuilder = Json.createArrayBuilder();
            // 1. OG Function, a traditional For Loop
            // for (String cookie : cookies) {
            // cookieBuilder.add(cookie);
            // }

            // 2. Fancy For Loop, featuring Lambda Expression.
            // cookies.stream()
            // .forEach(v -> {
            // cookieBuilder.add(v);
            // });

            // 3. I have no idea what is going on here.
            cookies.stream()
                    .reduce(
                            cookieBuilder, // identity
                            (ab, item) -> ab.add(item), // accumulator
                            (ab0, ab1) -> {
                                JsonArray a = ab1.build();
                                for (int i = 0; i < a.size(); i++) {
                                    ab0.add(a.get(i));
                                }
                                return ab0;
                            });

            JsonObject goodCookie = Json.createObjectBuilder()
                    .add("cookies", cookieBuilder.build())
                    .add("timeStamp", LocalDateTime.now().toString())
                    .build();

            responseEntity = ResponseEntity
                    .ok()
                    .body(goodCookie.toString());
        }
        return responseEntity;
    }
}
