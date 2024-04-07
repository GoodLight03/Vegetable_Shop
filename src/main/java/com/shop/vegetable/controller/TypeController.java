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
}
