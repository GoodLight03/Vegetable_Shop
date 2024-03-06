package com.shop.vegetable.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.vegetable.dto.CommentDto;
import com.shop.vegetable.dto.ProductDto;
import com.shop.vegetable.dto.RoleDto;
import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.Comment;
import com.shop.vegetable.entity.Product;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.ShoppingCart;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.log.LogFactory;
import com.shop.vegetable.log.PageVisitor;
import com.shop.vegetable.service.CommentService;
import com.shop.vegetable.service.ProductService;
import com.shop.vegetable.service.RoleService;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ClientController {
  private final UserService us;
  private final TypeService usk;
  private final ProductService prd;
  private final RoleService rl;
  private final CommentService cm;
  private static final Logger logger=LogFactory.getLogger();

  @GetMapping("/")
  public String homes(Model model, Principal principal, Authentication authentication, HttpSession session) {
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
      Users uskt = us.findByUsername(principal.getName());
      List<Role> rl = uskt.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
        if (rl.get(0).getName().equals("CUSTOMER")) {
          ShoppingCart cart = uskt.getCart();
          if(cart !=null){
            session.setAttribute("totalItems", cart.getTotalItems());
          }
          
        }
      }
      logger.info(uskt.getUsername()+" signed in");

      
      
    }
    List<Type> courses = usk.findAll();
    model.addAttribute("type", courses);

    List<Product> products = prd.findAll();

    List<Product> productty = new ArrayList<Product>();
    for (Product product : products) {
      if (product.getType().getName().equals("Củ Quả")) {
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
    model.addAttribute("currentPages", "home");
    // Users Users = us.findByUsername(principal.getName());
    // if(Users!=null){
    // ShoppingCart cart = Users.getCart();
    // session.setAttribute("totalItems", cart.getTotalItems());
    // }
    // session.setAttribute("totalItems", 0);
    return "client/home";
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
      if (product.getType().getId().equals(id) && product.getType().getName().equals("Củ Quả")) {
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

    return "client/home";
  }

  @GetMapping("/admin")
  public String ad() {
    return "admin/indexAd";
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
    model.addAttribute("currentPages", "contact");
    return "client/contact";
  }

  @GetMapping("/shop")
  public String sh(int pageNo, Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users uskh = us.findByUsername(principal.getName());
      List<Role> rl = uskh.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
      }
      PageVisitor pageVisitor=new PageVisitor();
      Date date=new Date();
      pageVisitor.visit(uskh.getUsername(), date);
      
    }
    List<Type> courses = usk.findAll();
    model.addAttribute("type", courses);

    // List<Product> products = prd.findAll();
    // List<ProductDto> products = prd.allProduct();
    Page<ProductDto> products = prd.getAllProducts(pageNo);
    model.addAttribute("currentPage", pageNo);
    model.addAttribute("totalPages", products.getTotalPages());
    model.addAttribute("product", products);
    model.addAttribute("currentPages", "shop");
    return "client/shop";
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

    List<Comment> lstcm = cm.findAll(productbs.getId());
    model.addAttribute("lstcomment", lstcm);
    model.addAttribute("product", productbs);
    model.addAttribute("commentDto", new CommentDto());

    return "client/detail";
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
    return "client/checkout";
  }

  @PostMapping("/save-comment")
  public String addLevel(@RequestParam("idd") Long idd, @ModelAttribute("commentDto") CommentDto commentDto,
      RedirectAttributes redirectAttributes, Principal principal, HttpServletRequest request) {
    try {
      Users usk = us.findByUsername(principal.getName());
      Product product = prd.getByIdNotDto(idd);
      commentDto.setUsers(usk);
      commentDto.setCommentDate(new Date());
      commentDto.setProduct(product);
      cm.save(commentDto);
      // redirectAttributes.addFlashAttribute("success", "Add new level
      // successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("error", "Failed to add new level!");
    }
    return "redirect:" + request.getHeader("Referer");

  }

  @RequestMapping(value = "/delete-comment", method = { RequestMethod.GET, RequestMethod.PUT })
  public String deletecm(Long id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
    try {
      cm.deletComment(id);
      redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
    } catch (DataIntegrityViolationException e1) {
      e1.printStackTrace();
      redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
    } catch (Exception e2) {
      e2.printStackTrace();
      redirectAttributes.addFlashAttribute("error", "Error server");
    }
    return "redirect:" + request.getHeader("Referer");
  }

}
