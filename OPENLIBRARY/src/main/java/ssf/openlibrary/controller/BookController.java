package ssf.openlibrary.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ssf.openlibrary.service.BookService;

import static ssf.openlibrary.util.Constants.*;

@Controller
@RequestMapping(path = { "/book" }, produces = MediaType.TEXT_HTML_VALUE)
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    @Qualifier(BEAN_LIBRARY_SERVICE)
    BookService bookSvc;

    @GetMapping(value = "/{key}")
    public String index(@PathVariable String key, Model model) {
        model.addAttribute("book", bookSvc.getBook(key));
        return "book";
    }
}
