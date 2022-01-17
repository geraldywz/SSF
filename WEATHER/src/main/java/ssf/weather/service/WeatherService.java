package ssf.weather.service;

import java.util.List;

import ssf.weather.model.Weather;

public interface WeatherService {

    public List<Weather> getWeather(String city);
    
}
