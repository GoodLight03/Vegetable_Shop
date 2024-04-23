package com.shop.vegetable.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.entity.Role;
import com.shop.vegetable.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/role")
@RequiredArgsConstructor
public class RoleApi {
    @Autowired
    private RoleService roleService;


    @GetMapping("/all")
    public ResponseEntity<Role> findAllUser(@RequestParam String namerole) {
        return ResponseEntity.ok(roleService.findByNameRole(namerole));
    } 
}
