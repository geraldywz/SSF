package ssf.weather.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ssf.weather.model.Weather;

import static ssf.weather.util.Constants.*;

@Service(BEAN_FETCH_WEATHER_SERVICE)
public class FetchWeatherService implements WeatherService {

    private final Logger logger = LoggerFactory.getLogger(FetchWeatherService.class);

    @Autowired
    @Qualifier(BEAN_WEATHER_SERVICE)
    private OpenWeatherService delegate;

    @Autowired
    private CacheWeatherService cache;

    public List<Weather> getWeather(String city) {
        logger.info("Fetching >>>>> Weather Service.");

        Optional<List<Weather>> opt = cache.get(city);

        List<Weather> weatherList = Collections.emptyList();

        if (opt.isPresent()) {
            logger.info("Cache hit for %s".formatted(city));
            weatherList = opt.get();
        } else
            try {
                weatherList = delegate.getWeather(city);
                if (weatherList.size() > 0)
                    cache.save(city, weatherList);
            } catch (Exception ex) {
                logger.warn("PARSING >>>>> ", ex);
            }
            
        return weatherList;
    }

}
