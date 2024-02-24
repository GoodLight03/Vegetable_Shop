package com.shop.vegetable.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.CommentService;
import com.shop.vegetable.service.ProductService;
import com.shop.vegetable.service.RoleService;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ClientController {
  private final UserService us;
  private final TypeService usk;
  private final ProductService prd;
  private final RoleService rl;
  private final CommentService cm;

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
      Users uskt = us.findByUsername(principal.getName());
      List<Role> rl = uskt.getRoles();
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

    List<Comment> lstcm=cm.findAll(productbs.getId());
    model.addAttribute("lstcomment", lstcm);
    model.addAttribute("product", productbs);
    model.addAttribute("commentDto", new CommentDto());
    

    return "detail";
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

  @PostMapping("/save-comment")
    public String addLevel(@RequestParam("idd") Long idd, @ModelAttribute("commentDto") CommentDto commentDto,
            RedirectAttributes redirectAttributes, Principal principal,HttpServletRequest request) {
        try {
            Users usk = us.findByUsername(principal.getName());
            Product product = prd.getByIdNotDto(idd);
            commentDto.setUsers(usk);
            commentDto.setCommentDate(new Date());
            commentDto.setProduct(product);
            cm.save(commentDto);
            //redirectAttributes.addFlashAttribute("success", "Add new level successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new level!");
        }
        return "redirect:" + request.getHeader("Referer");

    }

    @RequestMapping(value = "/delete-comment", method = {RequestMethod.GET, RequestMethod.PUT})
    public String deletecm(Long id, RedirectAttributes redirectAttributes,HttpServletRequest request) {
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
        return "redirect:"+request.getHeader("Referer");
    }

}
