package ssf.openlibrary.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssf.openlibrary.model.Book;
import ssf.openlibrary.repositories.BookRepository;

@Service
public class CacheBookService {

    private final Logger logger = LoggerFactory.getLogger(CacheBookService.class);

    @Autowired
    private BookRepository bookRepo;

    public void save(Book b) {
        bookRepo.save(b.getKey(), b.toJson().toString());
    }

    public Optional<Book> get(String key) {
        Optional<String> json = bookRepo.get(key);
        if (json.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(Book.create(json.get()));
    }
}
