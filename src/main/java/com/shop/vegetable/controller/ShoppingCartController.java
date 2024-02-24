package com.shop.vegetable.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shop.vegetable.dto.ProductDto;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.ShoppingCart;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.ProductService;
import com.shop.vegetable.service.ShoppingCartService;
import com.shop.vegetable.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService cartService;
    private final ProductService productService;
    private final UserService UsersService;
    private final UserService us;
    @GetMapping("/carts")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("namelogin", principal.getName());
            Users usk = us.findByUsername(principal.getName());
            List<Role> rl = usk.getRoles();
            if (rl.size() == 1) {
                model.addAttribute("rolelogin", rl.get(0).getName());
            }
        }
        Users Users = UsersService.findByUsername(principal.getName());
        ShoppingCart cart = Users.getCart();
        if (cart == null) {
            model.addAttribute("check");

        }
        if (cart != null) {
            model.addAttribute("grandTotal", cart.getTotalPrice());
        }
        model.addAttribute("shoppingCart", cart);
        model.addAttribute("title", "Cart");
        session.setAttribute("totalItems", cart.getTotalItems());
        return "cart";

    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            HttpServletRequest request,
            Model model,
            Principal principal,
            HttpSession session) {

        ProductDto productDto = productService.getById(id);
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long id,
            @RequestParam("quantity") int quantity,
            Model model,
            Principal principal,
            HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        ProductDto productDto = productService.getById(id);
        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.updateCart(productDto, quantity, username);
        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:/carts";

    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Long id,
            Model model,
            Principal principal,
            HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getById(id);
            String username = principal.getName();
            ShoppingCart shoppingCart = cartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            return "redirect:/carts";
        }
    }

}
