package ssf.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ssf.library.model.Book;
import ssf.library.model.Author;

@Service
public class LibraryRepo {

    private static final String CONTACT_ENTITY = "library";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void save(final Book book) {
        redisTemplate.opsForList().leftPush(CONTACT_ENTITY, book.getISBN());
        redisTemplate.opsForHash().put(CONTACT_ENTITY + "_Map", book.getISBN(), book);
    }

    public Book findById(final String isbn) {
        Book result = (Book) redisTemplate.opsForHash()
                .get(CONTACT_ENTITY + "_Map", isbn);
        return result;
    }

    public Optional<List<Book>> getAddressBook() {
        List<Object> fromContactList = redisTemplate.opsForList()
                .range(CONTACT_ENTITY, 0, -1);

        List<Book> addressBook = (List<Book>) redisTemplate.opsForHash()
                .multiGet(CONTACT_ENTITY + "_Map", fromContactList)
                .stream()
                .filter(Book.class::isInstance)
                .map(Book.class::cast)
                .toList();
        return Optional.of(addressBook);
    }
}
