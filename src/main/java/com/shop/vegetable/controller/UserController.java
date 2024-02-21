package com.shop.vegetable.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor


public class UserController {
    private final UserService us;
    @RequestMapping("/user")
    public String lst(Model model){
         model.addAttribute("title", "Manage User");
        List<Users> userss = us.findALl();
        model.addAttribute("usersss", userss);
        model.addAttribute("size", userss.size());
        model.addAttribute("usernew", new Users());
        return "users";
       
    }


}
