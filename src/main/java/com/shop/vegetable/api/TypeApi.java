package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.dto.ResponseObject;
import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.service.TypeService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/type")
public class TypeApi {
    @Autowired
    private TypeService typeService;

    @GetMapping("/all")
    @Operation(summary = "Get All Type")
    public ResponseEntity<?> findAllType() {
        List<Type> lt = typeService.findAll();
        return ResponseEntity.ok(lt);
    }

    // @GetMapping("/find")
    // public ResponseEntity<?> findAllTypeHi(@RequestParam(value="key",required = false) String key) {
    //     List<Type> lt = typeService.findCourses(key);
    //     return ResponseEntity.ok(lt);
    // }

    @GetMapping("/find/{key}")
    public ResponseEntity<?> findAllTypei(@PathVariable String key) {
        List<Type> lt = typeService.findCourses(key);
        return ResponseEntity.ok(lt);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Type> findTypeById(@PathVariable long id) {
        Optional<Type> m = typeService.findById(id);
        if (m.isPresent()) {
            return ResponseEntity.ok(m.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }

    @PostMapping("/save")
    public ResponseEntity<Type> addType(@RequestBody TypeDto typeDto) {
        Type mcd = typeService.save(typeDto);
        try {
            return ResponseEntity.created(new URI("/api/type/save/" + mcd.getId())).body(mcd);

        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateType(@RequestBody TypeDto type) {
        try {
            Type type2=new Type();
            type2.setId(type.getId());
            type2.setName(type.getName());
            typeService.update(type2);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
       
    }


    @DeleteMapping(path = "/all/{id}")
    public ResponseEntity<Void> deleteTypeById(@PathVariable long id) {
        try {
            typeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
