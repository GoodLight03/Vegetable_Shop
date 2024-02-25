package com.shop.vegetable.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.vegetable.dto.ProductDto;
import com.shop.vegetable.entity.Product;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.ProductService;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService lv;
    private final TypeService cs;
    private final UserService us;

    @RequestMapping("/product")
    public String lst(Model model,Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Manage Level");
        //List<Product> level = lv.findAll();
        List<ProductDto> level = lv.allProduct();
        List<Type> course = cs.findAll();
        model.addAttribute("level", level);
        model.addAttribute("course", course);
        model.addAttribute("size", level.size());
        model.addAttribute("levelDto", new ProductDto());
        return "product";

    }

    @GetMapping("/product/{pageNo}")
    public String allProducts(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = lv.getAllProducts(pageNo);
        model.addAttribute("title", "Manage Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("level", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "product";
    }

    @RequestMapping("/search_probytype")
    public String search(Long id, Model model, Principal principal, Authentication authentication) {
        if (principal != null) {
            model.addAttribute("namelogin", principal.getName());
            Users usk = us.findByUsername(principal.getName());
            List<Role> rl = usk.getRoles();
            if (rl.size() == 1) {
                model.addAttribute("rolelogin", rl.get(0).getName());
            }

        }
        List<Type> courses = cs.findAll();
        model.addAttribute("type", courses);

        List<Product> products = lv.findAll();

        List<Product> productveg = new ArrayList<Product>();
        for (Product product : products) {
            if (product.getType().getId().equals(id)) {
                productveg.add(product);
            }
        }

        model.addAttribute("product", productveg);
        return "shop";

    }

    @GetMapping("/search-product")
    public String searchProduct(
            @RequestParam(value = "keyword") String keyword,
            Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("namelogin", principal.getName());
            Users usk = us.findByUsername(principal.getName());
            List<Role> rl = usk.getRoles();
            if (rl.size() == 1) {
                model.addAttribute("rolelogin", rl.get(0).getName());
            }

        }

        List<Product> product = lv.findName(keyword);
        model.addAttribute("product", product);
        return "shop";

    }

    @PostMapping("/save-product")
    public String addCourse(@ModelAttribute("levelDto") ProductDto course,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            RedirectAttributes redirectAttributes) {
        try {

            lv.save(imageProduct, course);
            redirectAttributes.addFlashAttribute("success", "Add new course successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new course!");
        }
        return "redirect:/product";

    }

    @RequestMapping(value = "/delete-product", method = { RequestMethod.GET, RequestMethod.PUT })
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            lv.delete(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of level, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/product";
    }

    @RequestMapping(value = "/findByProductId", method = { RequestMethod.PUT, RequestMethod.GET })
    @ResponseBody
    public Optional<Product> findProductId(Long id) {
        return lv.findById(id);
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Type> categories = cs.findAll();
        Optional<Product> vv=lv.findById(id);

        ProductDto productDto = new ProductDto();
        productDto.setId(vv.get().getId());
        productDto.setName(vv.get().getName());
        productDto.setPrice(vv.get().getPrice());
        productDto.setDescription(vv.get().getDescription());
        productDto.setImage(vv.get().getImage());

        model.addAttribute("title", "Add Product");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            lv.update(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/product";
    }

    @GetMapping("/search-products/{pageNo}")
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam(value = "keyword") String keyword,
                                Model model, Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = lv.searchProducts(pageNo, keyword);
        model.addAttribute("title", "Result Search Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "product-result";

    }



}
