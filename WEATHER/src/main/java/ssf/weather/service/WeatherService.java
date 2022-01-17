package ssf.weather.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import ssf.weather.model.Weather;

import static ssf.weather.util.Constants.*;

@Service
public class WeatherService {

    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final String appId;

    @Autowired
    private ApiService api;

    // @Autowired
    // private WeatherCacheService weatherCacheService;

    public WeatherService() {
        String k = KEY_OPENWEATHERMAP;
        if ((null != k) && (k.trim().length() > 0)) {
            appId = k;
            logger.info("OPENWEATHERMAP_KEY set");
        } else {
            appId = "Ipsum Lorem";
            logger.warn("OPENWEATHERMAP_KET not set");
        }
    }

    public List<Weather> getWeather(String city) {

        final String url = UriComponentsBuilder
                .fromUriString(URL_WEATHER)
                .queryParam("q", city.trim().replace(" ", "+"))
                .queryParam("appid", appId)
                .queryParam("units", "metric")
                .toUriString();

        final JsonObject jsonObject = api.getJsonObject(url);
        final JsonArray readings = jsonObject.getJsonArray("weather");
        final String cityName = jsonObject.getString("name");
        final float temperature = (float) jsonObject.getJsonObject("main").getJsonNumber("temp").doubleValue();
        return readings.stream()
                .map(v -> (JsonObject) v)
                .map(Weather::create)
                .map(w -> {
                    w.setCityName(cityName);
                    w.setTemperature(temperature);
                    return w;
                })
                .collect(Collectors.toList());
    }
}