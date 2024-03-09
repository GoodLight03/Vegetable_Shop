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
    private final ProductService productService;
    private final TypeService typeService;
    private final UserService userService;

    @RequestMapping("/product")
    public String lst(Model model,Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Manage Level");
        //List<Product> level = lv.findAll();
        List<ProductDto> productDtos = productService.allProduct();
        List<Type> types = typeService.findAll();
        model.addAttribute("level", productDtos);
        model.addAttribute("course", types);
        model.addAttribute("size", productDtos.size());
        model.addAttribute("levelDto", new ProductDto());
        return "admin/product";

    }

    @GetMapping("/product/{pageNo}")
    public String allProducts(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.getAllProducts(pageNo);
        model.addAttribute("title", "Manage Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("level", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "admin/product";
    }

    @RequestMapping("/search_probytype")
    public String search(Long id, Model model, Principal principal, Authentication authentication) {
        if (principal != null) {
            model.addAttribute("namelogin", principal.getName());
            Users users = userService.findByUsername(principal.getName());
            List<Role> roles = users.getRoles();
            if (roles.size() == 1) {
                model.addAttribute("rolelogin", roles.get(0).getName());
            }

        }
        List<Type> types = typeService.findAll();
        model.addAttribute("type", types);

        List<Product> products = productService.findAll();

        List<Product> productveg = new ArrayList<Product>();
        for (Product product : products) {
            if (product.getType().getId().equals(id)) {
                productveg.add(product);
            }
        }

        model.addAttribute("product", productveg);
        return "client/shop";

    }

    @GetMapping("/search-product")
    public String searchProduct(
            @RequestParam(value = "keyword") String keyword,
            Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("namelogin", principal.getName());
            Users users = userService.findByUsername(principal.getName());
            List<Role> roles = users.getRoles();
            if (roles.size() == 1) {
                model.addAttribute("rolelogin", roles.get(0).getName());
            }

        }

        List<Product> product = productService.findName(keyword);
        model.addAttribute("product", product);
        return "client/shop";

    }

    @PostMapping("/save-product")
    public String addCourse(@ModelAttribute("levelDto") ProductDto course,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            RedirectAttributes redirectAttributes) {
        try {

            productService.save(imageProduct, course);
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
            productService.delete(id);
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
        return productService.findById(id);
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Type> categories = typeService.findAll();
        Optional<Product> proOptional=productService.findById(id);

        ProductDto productDto = new ProductDto();
        productDto.setId(proOptional.get().getId());
        productDto.setName(proOptional.get().getName());
        productDto.setPrice(proOptional.get().getPrice());
        productDto.setDescription(proOptional.get().getDescription());
        productDto.setImage(proOptional.get().getImage());

        model.addAttribute("title", "Add Product");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "admin/update-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            productService.update(imageProduct, productDto);
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
        Page<ProductDto> products = productService.searchProducts(pageNo, keyword);
        model.addAttribute("title", "Result Search Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "product-result";

    }



}
