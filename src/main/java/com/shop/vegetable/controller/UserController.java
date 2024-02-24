package com.shop.vegetable.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.vegetable.dto.UserDto;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService us;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping("/user")
    public String lst(Model model) {
        model.addAttribute("title", "Manage User");
        List<Users> userss = us.findALl();
        model.addAttribute("usersss", userss);
        model.addAttribute("size", userss.size());
        model.addAttribute("usernew", new Users());
        return "users";

    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("namelogin", principal.getName());
            Users usk = us.findByUsername(principal.getName());
            List<Role> rl = usk.getRoles();
            if (rl.size() == 1) {
                model.addAttribute("rolelogin", rl.get(0).getName());
            }
        }
        String username = principal.getName();
        UserDto customer = us.getCustomer(username);

        model.addAttribute("customer", customer);

        model.addAttribute("title", "Profile");
        model.addAttribute("page", "Profile");
        return "users-information";

    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("customer") UserDto userDto,
            // BindingResult result,
            RedirectAttributes attributes,
            Model model,
            Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        UserDto customer = us.getCustomer(username);
        customer.setAddress(userDto.getAddress());
        customer.setPhone(userDto.getPhone());
        // if (result.hasErrors()) {
        // return "users-information";
        // }
        us.update(customer);
        UserDto customerUpdate = us.getCustomer(principal.getName());
        attributes.addFlashAttribute("success", "Update successfully!");
        model.addAttribute("customer", customerUpdate);
        return "redirect:/profile";

    }

    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("repeatNewPassword") String repeatPassword,
            RedirectAttributes attributes,
            Model model,
            Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            UserDto customer = us.getCustomer(principal.getName());
            if (passwordEncoder.matches(oldPassword, customer.getPassword())
                    && !passwordEncoder.matches(newPassword, oldPassword)
                    && !passwordEncoder.matches(newPassword, customer.getPassword())
                    && repeatPassword.equals(newPassword) && newPassword.length() >= 5) {
                customer.setPassword(passwordEncoder.encode(newPassword));
                us.changePass(customer);
                attributes.addFlashAttribute("success", "Your password has been changed successfully!");
                return "redirect:/profile";
            } else {
                model.addAttribute("message", "Your password is wrong");
                return "/change-password";
            }
        }
    }

}
