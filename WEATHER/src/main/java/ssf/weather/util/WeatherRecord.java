package ssf.weather.util;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import ssf.weather.model.Weather;

public class WeatherRecord {

    // private static final Logger logger = LoggerFactory.getLogger(WeatherRecord.class);

    public static List<Weather> parseJSON(String json) {
        JsonArray weatherArray = Json.createReader(
                new StringReader(json))
                .readArray();
        return weatherArray.stream()
                .map(o -> (JsonObject) o)
                .map(o -> {
                    Weather w = Weather.create(o);
                    w.setCityName(o.getString("cityName"));
                    w.setTemperature((float) o.getJsonNumber("temperature").doubleValue());

                    return w;
                })
                .collect(Collectors.toList());
    }

    public static String encode(List<Weather> history) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Weather w : history) {
            arrayBuilder.add(w.toJson());
        }
        return arrayBuilder.build().toString();
    }

    public static String generateCity() {
        List<String> cityList = Arrays.asList(
                "Singapore",
                "Hong Kong",
                "New York",
                "Bankgkok",
                "Sydney",
                "Melbourne",
                "Taipei",
                "Beijing",
                "Seoul",
                "Jeju",
                "Myanmar",
                "Auckland",
                "Paris",
                "Barcelona",
                "Russia",
                "Alaska",
                "Washington",
                "Mexico",
                "Berlin",
                "Helsinki",
                "Spain",
                "Cambodia");
        Collections.shuffle(cityList);
        return cityList.get(0);
    }

}
