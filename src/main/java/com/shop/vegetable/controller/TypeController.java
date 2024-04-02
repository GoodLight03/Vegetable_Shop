package com.shop.vegetable.controller;

import java.security.Principal;
import java.util.List;

import java.util.Optional;


import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.TypeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TypeController {
    private final TypeService typeService;

    @GetMapping("/type")
    public String lst(Model model) {
        model.addAttribute("title", "Manage Course");
        List<Type> types = typeService.findAll();
        model.addAttribute("courses", types);
        model.addAttribute("size", types.size());
        model.addAttribute("usernew", new Users());
        model.addAttribute("courseDto", new TypeDto());
        model.addAttribute("currentPages", "types");
        return "admin/type";
    }

    

    @PostMapping("/save-type")
    public String addLevel(@ModelAttribute("courseDto") TypeDto typeDto,
            RedirectAttributes redirectAttributes) {
        try {
            typeService.save(typeDto);
            redirectAttributes.addFlashAttribute("success", "Add new level successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new level!");
        }
        return "redirect:/type";

    }

    @RequestMapping(value = "/findById", method = { RequestMethod.PUT, RequestMethod.GET })
    @ResponseBody
    public Optional<Type> findById(Long id) {
        return typeService.findById(id);
    }

    @GetMapping("/update-type")
    // @PutMapping("/update-type")
    public String update(Type course, RedirectAttributes redirectAttributes) {
        try {
            typeService.update(course);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Error from server or duplicate name of category, please check again!");
        }
        return "redirect:/type";
    }
    @RequestMapping(value = "/delete-type", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            typeService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/type";
    }

    @GetMapping("/search-type")
    public String searchProduct(
                                @RequestParam(value = "keyword") String keyword,
                                Model model, Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Manage Course");
        List<Type> types = typeService.findCourses(keyword);
        model.addAttribute("courses", types);
        model.addAttribute("size", types.size());
        model.addAttribute("usernew", new Users());
        model.addAttribute("courseDto", new TypeDto());
        return "admin/type";

    }

    
}
