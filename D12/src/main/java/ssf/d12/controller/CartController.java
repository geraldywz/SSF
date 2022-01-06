package ssf.d12.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ssf.d12.model.Item;

@Controller
@RequestMapping(path = { "/cart" })
public class CartController {

    @GetMapping(produces = { "text/html" })
    public String getCart(Model model) {
        setDate(model);
        model.addAttribute("cart", getShoppingCart());
        return "cart";
    }

    private void setDate(Model model) {
        Calendar cal = Calendar.getInstance();
        model.addAttribute("currTime", (new Date()).toString());
        model.addAttribute("currHour", cal.get(Calendar.HOUR_OF_DAY));
    }

    private List<Item> getShoppingCart() {
        List<Item> cart = new ArrayList<>();
        cart.add(new Item("Orange", 6));
        cart.add(new Item("Sausage", 5));
        cart.add(new Item("Diaper", 2));
        cart.add(new Item("Brocolli", 3));
        cart.add(new Item("Tomato", 4));
        cart.add(new Item("Detergent", 1));
        return cart;
    }

}
