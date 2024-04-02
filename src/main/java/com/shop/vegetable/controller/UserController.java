package com.shop.vegetable.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.vegetable.dto.UserDto;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping("/user")
    public String lst(Model model) {
        model.addAttribute("title", "Manage User");
        List<Users> userss = userService.findALl();
        model.addAttribute("usersss", userss);
        model.addAttribute("size", userss.size());
        model.addAttribute("usernew", new Users());
        model.addAttribute("currentPages", "users");
        return "admin/users";

    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("namelogin", principal.getName());
            Users users = userService.findByUsername(principal.getName());
            List<Role> roles = users.getRoles();
            if (roles.size() == 1) {
                model.addAttribute("rolelogin", roles.get(0).getName());
            }
        }
        String username = principal.getName();
        UserDto customer = userService.getCustomer(username);

        model.addAttribute("customer", customer);

        model.addAttribute("title", "Profile");
        model.addAttribute("page", "Profile");
        model.addAttribute("currentPages", "profile");
        return "client/users-information";

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
        UserDto customer = userService.getCustomer(username);
        customer.setAddress(userDto.getAddress());
        customer.setPhone(userDto.getPhone());
        // if (result.hasErrors()) {
        // return "users-information";
        // }
        userService.update(customer);
        UserDto customerUpdate = userService.getCustomer(principal.getName());
        attributes.addFlashAttribute("success", "Update successfully!");
        model.addAttribute("customer", customerUpdate);
        return "redirect:/profile";

    }

    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("namelogin", principal.getName());
            Users users = userService.findByUsername(principal.getName());
            List<Role> roles = users.getRoles();
            if (roles.size() == 1) {
                model.addAttribute("rolelogin", roles.get(0).getName());
            }
        }
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");
        model.addAttribute("currentPages", "changepass");
        return "auth/change-password";
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
            UserDto customer = userService.getCustomer(principal.getName());
            if (passwordEncoder.matches(oldPassword, customer.getPassword())
                    && !passwordEncoder.matches(newPassword, oldPassword)
                    && !passwordEncoder.matches(newPassword, customer.getPassword())
                    && repeatPassword.equals(newPassword) && newPassword.length() >= 5) {
                customer.setPassword(passwordEncoder.encode(newPassword));
                userService.changePass(customer);
                attributes.addFlashAttribute("success", "Your password has been changed successfully!");
                return "redirect:/profile";
            } else {
                model.addAttribute("message", "Your password is wrong");
                return "/change-password";
            }
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete-user/{id}")
    @ResponseBody
    public void delete(@PathVariable(value = "id", required = false) long id) {  
        userService.delete(id);
    }

}
