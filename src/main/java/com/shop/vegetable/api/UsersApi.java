package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.dto.ResponseObject;
import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.dto.UserDto;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.RoleService;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UsersApi {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public ResponseEntity<List<Users>> findAllUser() {
        return ResponseEntity.ok(userService.findALl());
    }

    @GetMapping("/all/{name}")
    public ResponseEntity<UserDto> findUserByName(@PathVariable String name) {
        UserDto m = userService.getCustomer(name);
        if (m != null) {
            return ResponseEntity.ok(m);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
    }

    @GetMapping("/all/role")
    public ResponseEntity<List<Users>> findUserByRole(@RequestParam String namerole) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByNameRole(namerole));
        List<Users> users = userService.findByRole(roles);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseObject> addType(@RequestBody @Valid UserDto userDto, BindingResult result) {
        ResponseObject ro = new ResponseObject();
        if (result.hasErrors()) {
            setErrorsForResponseObject(result, ro);
        } else {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            ro.setStatus("success");
            userService.save(userDto);
        }
        return ResponseEntity.ok(ro);
        // Type mcd = typeService.save(typeDto);
        // try {
        // return ResponseEntity.created(new URI("/api/type/save/" +
        // mcd.getId())).body(mcd);

        // } catch (URISyntaxException e) {
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        // }
    }

    public void setErrorsForResponseObject(BindingResult result, ResponseObject object) {

        Map<String, String> errors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        object.setErrorMessages(errors);
        object.setStatus("fail");

        List<String> keys = new ArrayList<String>(errors.keySet());
        for (String key : keys) {
            System.out.println(key + ": " + errors.get(key));
        }

        errors = null;
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
