package ssf.weather.repositories;

import static ssf.weather.util.Constants.*;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WeatherRepo {

    @Autowired
    @Qualifier(BEAN_WEATHER_CACHE)
    private RedisTemplate<String, String> template;

    public void save(String cityName, String value) {
        template
                .opsForValue()
                .set(normalize(cityName), value, 10L, TimeUnit.MINUTES);
    }

    public Optional<String> get(String cityName) {
        String value = template.opsForValue().get(normalize(cityName));
        return Optional.ofNullable(value);
    }

    private String normalize(String k) {
        return k.trim().toLowerCase();
    }

}
