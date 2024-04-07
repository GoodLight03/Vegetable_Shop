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
    private final UserService usersService;

    @GetMapping("/carts")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("namelogin", principal.getName());
            Users users = usersService.findByUsername(principal.getName());
            List<Role> roles = users.getRoles();
            if (roles.size() == 1) {
                model.addAttribute("rolelogin", roles.get(0).getName());
            }
        }
        Users Users = usersService.findByUsername(principal.getName());
        ShoppingCart cart = Users.getCart();
        if (cart == null) {
            cart=new ShoppingCart();
            cart.setUsers(Users);
            cartService.saveCart(cart);
            //model.addAttribute("check", 0);

        }
        
        if (cart != null) {
            
            model.addAttribute("grandTotal", cart.getTotalPrice());
            model.addAttribute("shoppingCart", cart);
            model.addAttribute("title", "Cart");
            session.setAttribute("totalItems", cart.getTotalItems());
        }

        return "client/cart";

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
