package com.shop.vegetable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.vegetable.dto.UserDto;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.UserService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService adminService;

    private final BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "auth/login";
    }



    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new UserDto());
        return "auth/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "auth/forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") UserDto adminDto,
            BindingResult result,
            Model model,RedirectAttributes redirectAttributes) {

        try {

            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                return "auth/register";
            }
            String username = adminDto.getUsername();
            Users admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                System.out.println("admin not null");
                model.addAttribute("emailError", "Your email has been registered!");
                return "auth/register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminDto.setRole("CUSTOMER");
                adminService.save(adminDto);
                // System.out.println("success");
                // model.addAttribute("success", "Register successfully!");
                // model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("success", "Register successfully!");
                return "redirect:/login";

            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "auth/register";

    }
}
