package com.shop.vegetable.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shop.vegetable.dto.RoleDto;
import com.shop.vegetable.entity.Product;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.ProductService;
import com.shop.vegetable.service.RoleService;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ClientController {
  private final UserService us;
  private final TypeService usk;
  private final ProductService prd;
  private final RoleService rl;

  @GetMapping("/")
  public String homes(Model model, Principal principal, Authentication authentication) {
    List<Role> ls = rl.findALl();
    if (ls.isEmpty()) {
      RoleDto ad = new RoleDto();
      ad.setName("ADMIN");
      rl.save(ad);
      us.saveAD();

      RoleDto cs = new RoleDto();
      cs.setName("CUSTOMER");
      rl.save(cs);
    }
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users usk = us.findByUsername(principal.getName());
      List<Role> rl = usk.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
      }

    }
    List<Type> courses = usk.findAll();
    model.addAttribute("type", courses);

    List<Product> products = prd.findAll();

    model.addAttribute("product", products);

    List<Product> productveg = new ArrayList<Product>();
    for (Product product : products) {
      if (product.getType().getName().equals("Rau")) {
        productveg.add(product);
      }
    }
    model.addAttribute("productvg", productveg);

    List<Product> productbs = new ArrayList<Product>();
    for (Product product : products) {
      if (product.getPrice() >= 30) {
        productbs.add(product);
      }
    }
    model.addAttribute("productbs", productbs);

    return "home";
  }

  @GetMapping("/check")
  public String home(Long id, Model model, Principal principal, Authentication authentication) {
    List<Role> ls = rl.findALl();
    if (ls.isEmpty()) {
      RoleDto ad = new RoleDto();
      ad.setName("ADMIN");
      rl.save(ad);
      us.saveAD();

      RoleDto cs = new RoleDto();
      cs.setName("CUSTOMER");
      rl.save(cs);
    }
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users usk = us.findByUsername(principal.getName());
      List<Role> rl = usk.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
      }

    }
    List<Type> courses = usk.findAll();
    model.addAttribute("type", courses);

    List<Product> products = prd.findAll();

    List<Product> productty = new ArrayList<Product>();
    for (Product product : products) {
      if (product.getType().getId().equals(id)) {
        productty.add(product);
      }
    }
    model.addAttribute("product", productty);

    List<Product> productveg = new ArrayList<Product>();
    for (Product product : products) {
      if (product.getType().getName().equals("Rau")) {
        productveg.add(product);
      }
    }
    model.addAttribute("productvg", productveg);

    List<Product> productbs = new ArrayList<Product>();
    for (Product product : products) {
      if (product.getPrice() >= 30) {
        productbs.add(product);
      }
    }
    model.addAttribute("productbs", productbs);

    return "home";
  }

  @GetMapping("/admin")
  public String ad() {
    return "indexAd";
  }

  @GetMapping("/contact")
  public String ct(Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users usk = us.findByUsername(principal.getName());
      List<Role> rl = usk.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
      }

    }
    List<Type> courses = usk.findAll();
    model.addAttribute("courses", courses);
    return "contact";
  }

  @GetMapping("/shop")
  public String sh(Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users usk = us.findByUsername(principal.getName());
      List<Role> rl = usk.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
      }

    }
    List<Type> courses = usk.findAll();
    model.addAttribute("type", courses);

    List<Product> products = prd.findAll();
    model.addAttribute("product", products);
    return "shop";
  }

  @GetMapping("/detail")
  public String dt(Long id, Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users usk = us.findByUsername(principal.getName());
      List<Role> rl = usk.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
      }

    }
    List<Type> courses = usk.findAll();
    model.addAttribute("type", courses);

    List<Product> products = prd.findAll();
    Product productbs = new Product();
    for (Product product : products) {
      if (product.getId().equals(id)) {
        productbs = product;
      }
    }
    model.addAttribute("product", productbs);

    return "detail";
  }

  @GetMapping("/cart")
  public String cr(Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users usk = us.findByUsername(principal.getName());
      List<Role> rl = usk.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
      }

    }
    List<Type> courses = usk.findAll();
    model.addAttribute("courses", courses);
    return "cart";
  }

  @GetMapping("/checkout")
  public String co(Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users usk = us.findByUsername(principal.getName());
      List<Role> rl = usk.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
      }

    }
    List<Type> courses = usk.findAll();
    model.addAttribute("courses", courses);
    return "checkout";
  }

}
