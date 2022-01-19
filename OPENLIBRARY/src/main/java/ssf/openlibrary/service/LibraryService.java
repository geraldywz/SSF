package ssf.openlibrary.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ssf.openlibrary.model.Book;

import static ssf.openlibrary.util.Constants.*;

@Service(BEAN_LIBRARY_SERVICE)
public class LibraryService implements BookService {

    private final Logger logger = LoggerFactory.getLogger(LibraryService.class);

    @Autowired
    @Qualifier(BEAN_BOOK_SERVICE)
    private OpenLibraryService delegate;

    @Autowired
    private CacheBookService cache;

    public List<Book> search(String title) {
        return delegate.search(title);
    }

    public Book getBook(String key) {
        Optional<Book> opt = cache.get(key);
        if (opt.isPresent()) {

            logger.info("Cache hit for %s".formatted(key));
            Book b = opt.get();
            b.setCached(true);

            return b;
        } else {

            Book b = delegate.getBook(key);
            cache.save(b);
            b.setCached(false);
            
            return b;
        }
    }
}
