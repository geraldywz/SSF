package ssf.openlibrary.repositories;

import static ssf.openlibrary.util.Constants.*;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    @Autowired
    @Qualifier(BEAN_BOOK_CACHE)
    private RedisTemplate<String, String> template;

    public void save(String key, String value) {
        template
                .opsForValue()
                .set(normalize(key), value, 10L, TimeUnit.MINUTES);
    }

    public Optional<String> get(String key) {
        String value = template.opsForValue().get(normalize(key));
        return Optional.ofNullable(value);
    }

    private String normalize(String k) {
        return k.trim().toLowerCase();
    }

}
