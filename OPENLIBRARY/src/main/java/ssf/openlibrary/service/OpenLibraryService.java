package ssf.openlibrary.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import ssf.openlibrary.model.Book;

import static ssf.openlibrary.util.Constants.*;

@Service(BEAN_BOOK_SERVICE)
public class OpenLibraryService implements BookService {

    private final Logger logger = LoggerFactory.getLogger(OpenLibraryService.class);

    @Autowired
    private ApiService api;

    public OpenLibraryService() {
    }

    public List<Book> search(String title) {
        final String url = UriComponentsBuilder
                .fromUriString(URL_OPENLIBRARY)
                .path("search.json")
                .queryParam("q", title.trim().replace(" ", "+"))
                .queryParam("limit", "20")
                .toUriString();
                logger.info(url);

        final JsonObject jsonObject = api.getJsonObject(url);
        final JsonArray docs = jsonObject.getJsonArray("docs");
        final List<Book> books = docs.stream()
                .map(jo -> (JsonObject) jo)
                .map(Book::create)
                .map(b -> {
                    logger.info("Key >>>>> " + b.getKey());
                    return b;
                })
                .collect(Collectors.toList());
        if (books != null && books.size() > 0) {
            return books;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public Book getBook(String key) {
        final String url = UriComponentsBuilder
                .fromUriString(URL_OPENLIBRARY)
                .path("works/")
                .path(key)
                .path(".json")
                .toUriString();
        final JsonObject jsonObject = api.getJsonObject(url);

        return Book.create(jsonObject);
    }
}
