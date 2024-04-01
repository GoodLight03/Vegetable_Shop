package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.Contact;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.service.ContactService;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.utils.EmailUlti;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
@RestController
@RequestMapping("api/contact")
public class ContactApi {
     @Autowired
    private ContactService contactService;

     @Autowired
	private EmailUlti emailUlti;

    @GetMapping("/all")
    public ResponseEntity<?> findAllType() {
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Contact> findTypeById(@PathVariable long id) {
        Optional<Contact> m = contactService.findById(id);
        if (m.isPresent()) {
            return ResponseEntity.ok(m.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }

    @PostMapping("/save")
    public ResponseEntity<Contact> addType(@RequestBody Contact contact) {
        Contact mcd = contactService.save(contact);
        try {
            return ResponseEntity.created(new URI("/api/contact/save/" + mcd.getId())).body(mcd);

        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/all/{id}")
    public ResponseEntity<Void> updateType(@RequestParam("reply")String reply, @PathVariable long id) {
        try {
            contactService.update(reply,new Date(),id);
            Contact contact=contactService.findById(id).get();
            emailUlti.sendEmail(contact.getEmail(),contact.getTitle(),reply);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/all")
    public ResponseEntity<?> updatehType(@RequestBody Contact reply) {
        try {
            Contact contact=contactService.updateAll(reply);
            return ResponseEntity.ok(contact);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id, @Valid @RequestBody Contact request){
        Contact contact=contactService.findById(id).get();
        contact.setContent(request.getContent());
        Contact category = contactService.updateAll(contact);
        return ResponseEntity.ok(category);
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

    // @DeleteMapping(path = "/all/{id}")
    // public ResponseEntity<Void> deleteTypeById(@PathVariable long id) {
    //     try {
    //         typeService.delete(id);
    //         return ResponseEntity.ok().build();
    //     } catch (EntityNotFoundException ex) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

}
