package ssf.weather.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ssf.weather.model.Weather;
import ssf.weather.service.WeatherService;
import ssf.weather.util.WeatherRecord;

import static ssf.weather.util.Constants.*;

@Controller
@RequestMapping(path = { "/", "/weather" }, produces = MediaType.TEXT_HTML_VALUE)
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    @Qualifier(BEAN_FETCH_WEATHER_SERVICE)
    WeatherService weatherSvc;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("city", WeatherRecord.generateCity());
        return "weather";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getWeather(@RequestBody MultiValueMap<String, String> form, Model model) {
        String city = form.getFirst("city");
        String history = form.getFirst("history").trim();

        List<Weather> weatherList;

        if (history == null || history.length() == 0) {
            logger.info("WeatherHistory >>>>> New");
            weatherList = new ArrayList<>();
        } else {
            logger.info("WeatherHistory >>>>> Parse");
            weatherList = WeatherRecord.parseJSON(history);
        }
        weatherList.add(0, weatherSvc.getWeather(city).get(0));
        if (weatherList.size() > 5) {
            weatherList.remove(weatherList.size() - 1);
        }
        logger.info("Weather History >>>>> " + weatherList.size());

        model.addAttribute("history", WeatherRecord.encode(weatherList));
        model.addAttribute("contents", weatherList);
        model.addAttribute("city", WeatherRecord.generateCity());
        return "weather";
    }
}
