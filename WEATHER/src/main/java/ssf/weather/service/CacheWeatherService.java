package ssf.weather.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import ssf.weather.model.Weather;
import ssf.weather.repositories.WeatherRepo;

@Service
public class CacheWeatherService {

    private final Logger logger = LoggerFactory.getLogger(CacheWeatherService.class);

    @Autowired
    private WeatherRepo weatherRepo;

    public void save(String cityName, List<Weather> weather) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        weather.stream()
                .forEach(w -> arrBuilder.add(w.toJson()));
        weatherRepo.save(cityName, arrBuilder.build().toString());
    }

    public Optional<List<Weather>> get(String cityName) {
        Optional<String> opt = weatherRepo.get(cityName);
        if (opt.isEmpty())
            return Optional.empty();

        JsonArray jsonArray = parseJsonArray(opt.get());
        List<Weather> weather = jsonArray.stream()
                .map(w -> (JsonObject) w)
                .map(Weather::create)
                .collect(Collectors.toList());
        return Optional.of(weather);
    }

    private JsonArray parseJsonArray(String s) {
        try (InputStream is = new ByteArrayInputStream(s.getBytes())) {
            JsonReader reader = Json.createReader(is);
            return reader.readArray();
        } catch (Exception ex) {
            logger.warn("PARSING >>>>> ", ex);
        }
        return Json.createArrayBuilder().build();
    }

}
