package ssf.openlibrary.controller;

import java.util.Arrays;
import java.util.Collections;
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

import ssf.openlibrary.service.BookService;

import static ssf.openlibrary.util.Constants.*;

@Controller
@RequestMapping(path = { "/" }, produces = MediaType.TEXT_HTML_VALUE)
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    @Qualifier(BEAN_LIBRARY_SERVICE)
    BookService bookSvc;

    @GetMapping
    public String index(Model model) {
        List<String> titleList = Arrays.asList(
                "Harry Potter",
                "Beowulf",
                "Megaman",
                "Astro Boy",
                "Dragon Ball",
                "Ragnarok",
                "Slam Dunk",
                "Sailor Moon",
                "Silver Surfer",
                "Hulk",
                "Captain America",
                "Thor",
                "Wonder Woman",
                "Batman",
                "Scarlet Witch",
                "Loki",
                "Iron Man",
                "Java",
                "Data Structures",
                "Algorithms");
        Collections.shuffle(titleList);
        model.addAttribute("title", titleList.get(0));
        return "index";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String search(@RequestBody MultiValueMap<String, String> form, Model model) {
        String title = form.getFirst("title").trim();
        model.addAttribute("url", "book/");
        model.addAttribute("title", title);
        model.addAttribute("books", bookSvc.search(title));
        return "results";
    }
}
