package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.dto.UserDto;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/user")
public class UsersApi {
     @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<Users>> findAllUser() {
        return ResponseEntity.ok(userService.findALl());
    }

    @GetMapping("/all/{name}")
    public ResponseEntity<UserDto> findUserByName(@PathVariable String name) {
        UserDto m = userService.getCustomer(name);
        if (m!=null) {
            return ResponseEntity.ok(m);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }

    @PostMapping("/save")
    public ResponseEntity<Users> addUser(@RequestBody UserDto userDto) {
        Users mcd = userService.save(userDto);
        try {
            return ResponseEntity.created(new URI("/api/type/save/" + mcd.getId())).body(mcd);

        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/all/{name}")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto, @PathVariable String username) {
        try {
            UserDto customer = userService.getCustomer(username);
        customer.setAddress(userDto.getAddress());
        customer.setPhone(userDto.getPhone());
            userService.update(userDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // @PatchMapping("/all/{id}")
    // public ResponseEntity<Void> updateMedicineName(@RequestBody String
    // nameString, @PathVariable long id) {
    // try {
    // typeService.updateName(id, nameString);
    // return ResponseEntity.ok().build();
    // } catch (EntityNotFoundException ex) {
    // return ResponseEntity.notFound().build();
    // }
    // }

    @DeleteMapping(path = "/all/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
