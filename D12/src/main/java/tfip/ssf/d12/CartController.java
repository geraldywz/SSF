package tfip.ssf.d12;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = { "/cart" })
public class CartController {

    @GetMapping(produces = { "text/html" })
    public String getCart(Model model) {
        model.addAttribute("cart", getShoppingCart());
        return "cart";
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
