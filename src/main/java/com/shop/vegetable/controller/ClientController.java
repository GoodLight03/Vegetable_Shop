package com.shop.vegetable.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.shop.vegetable.exception.RecordNotFoundException;
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
  private final UserService userService;
  private final TypeService typeService;
  private final ProductService productService;
  private final RoleService roleService;
  private final CommentService commentService;
  private static final Logger logger = LogFactory.getLogger();

  @GetMapping("/")
  public String homes(Model model, Principal principal, Authentication authentication, HttpSession session) {
    List<Role> roles = roleService.findALl();
    if (roles.isEmpty()) {
      addAdminAndCustomer();
    }
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users uskt = userService.findByUsername(principal.getName());
      List<Role> rl = uskt.getRoles();
      if (rl.size() == 1) {
        model.addAttribute("rolelogin", rl.get(0).getName());
        if (rl.get(0).getName().equals("CUSTOMER")) {
          ShoppingCart cart = uskt.getCart();
          if (cart != null) {
            session.setAttribute("totalItems", cart.getTotalItems());
          }

        }
      }
      logger.info(uskt.getUsername() + " signed in");

    }
    List<Type> courses = typeService.findAll();
    model.addAttribute("type", courses);

    List<Product> products = productService.findAll();

    List<Product> productty = products.stream()
        .filter(product -> product.getType().getName().equals("Củ Quả"))
        .collect(Collectors.toList());

    model.addAttribute("product", productty);

    List<Product> productveg = products.stream()
    .filter(product -> product.getType().getName().equals("Rau"))
    .collect(Collectors.toList());

    model.addAttribute("productvg", productveg);

    List<Product> productbs = products.stream()
        .filter(product -> product.getPrice()>=50)
        .collect(Collectors.toList());

    model.addAttribute("productbs", productbs);
    model.addAttribute("currentPages", "home");
    return "client/home";
  }

  public void addAdminAndCustomer(){
    RoleDto adminRole = new RoleDto();
    adminRole.setName("ADMIN");
    roleService.save(adminRole);
    userService.saveAD();

    RoleDto customerRole = new RoleDto();
    customerRole.setName("CUSTOMER");
    roleService.save(customerRole);
  }

  @GetMapping("/admin")
  public String ad() {
    return "admin/indexAd";
  }

  @GetMapping("/contact")
  public String ct(Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users users = userService.findByUsername(principal.getName());
      List<Role> roles = users.getRoles();
      if (roles.size() == 1) {
        model.addAttribute("rolelogin", roles.get(0).getName());
      }

    }
    List<Type> courses = typeService.findAll();
    model.addAttribute("courses", courses);
    model.addAttribute("currentPages", "contact");
    return "client/contact";
  }

  @GetMapping("/shop")
  public String sh(int pageNo, Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users users = userService.findByUsername(principal.getName());
      List<Role> roles = users.getRoles();
      if (roles.size() == 1) {
        model.addAttribute("rolelogin", roles.get(0).getName());
      }
      PageVisitor pageVisitor = new PageVisitor();
      Date date = new Date();
      pageVisitor.visit(users.getUsername(), date);

    }
    List<Type> courses = typeService.findAll();
    model.addAttribute("type", courses);

    // List<Product> products = prd.findAll();
    // List<ProductDto> products = prd.allProduct();
    Page<ProductDto> products = productService.getAllProducts(pageNo);
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
      Users users = userService.findByUsername(principal.getName());
      List<Role> roles = users.getRoles();
      if (roles.size() == 1) {
        model.addAttribute("rolelogin", roles.get(0).getName());
      }

    }
    List<Type> courses = typeService.findAll();
    model.addAttribute("type", courses);

    List<Product> products = productService.findAll();
    Product productbs = new Product();
    for (Product product : products) {
      if (product.getId().equals(id)) {
        productbs = product;
      }
    }
    // if(productbs==null)
    //       throw new RecordNotFoundException();
    

    List<Comment> comments = commentService.findAll(productbs.getId());
    model.addAttribute("lstcomment", comments);
    model.addAttribute("product", productbs);
    model.addAttribute("commentDto", new CommentDto());

    return "client/detail";
  }

  @GetMapping("/checkout")
  public String co(Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users users = userService.findByUsername(principal.getName());
      List<Role> roles = users.getRoles();
      if (roles.size() == 1) {
        model.addAttribute("rolelogin", roles.get(0).getName());
      }

    }
    List<Type> courses = typeService.findAll();
    model.addAttribute("courses", courses);
    return "client/checkout";
  }

  @PostMapping("/save-comment")
  public String addLevel(@RequestParam("idd") Long idd, @ModelAttribute("commentDto") CommentDto commentDto,
      RedirectAttributes redirectAttributes, Principal principal, HttpServletRequest request) {
    if (principal == null) {
      return "redirect:/login";
    }
    try {
      Users users = userService.findByUsername(principal.getName());
      Product product = productService.getByIdNotDto(idd);
      commentDto.setUsers(users);
      commentDto.setCommentDate(new Date());
      commentDto.setProduct(product);
      commentService.save(commentDto);
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
      commentService.deletComment(id);
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

  @GetMapping("/errors")
  public String cet(Model model) {
    model.addAttribute("status", 403);
    model.addAttribute("errorMessage", "Bạn không có quyền!");
    return "auth/error";
  }

}
