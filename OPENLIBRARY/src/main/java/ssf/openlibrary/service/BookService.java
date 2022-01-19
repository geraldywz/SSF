package ssf.openlibrary.service;

import java.util.List;

import ssf.openlibrary.model.Book;

public interface BookService {

    public List<Book> search(String title);
    
    public Book getBook(String key);
}
