package com.shop.vegetable.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.Contact;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.CommentService;
import com.shop.vegetable.service.ContactService;
import com.shop.vegetable.service.ProductService;
import com.shop.vegetable.service.RoleService;
import com.shop.vegetable.service.TypeService;
import com.shop.vegetable.service.UserService;
import com.shop.vegetable.utils.EmailUlti;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ContactController {
  private final UserService userService;
  private final TypeService typeService;
  private final ProductService productService;
  private final RoleService roleService;
  private final CommentService commentService;
  private final ContactService contactService;

  @Autowired
	private EmailUlti emailUlti;

    //@Secured("ROLE_ADMIN")
  //@RolesAllowed("ADMIN")
  @GetMapping("/contact")
  public String ct(Model model, Principal principal, Authentication authentication) {
    if (principal != null) {
      model.addAttribute("namelogin", principal.getName());
      Users users = userService.findByUsername(principal.getName());
      List<Role> roles = users.getRoles();
      if (roles.size() == 1) {
        model.addAttribute("rolelogin", roles.get(0).getName());
      }
    }
    List<Type> courses = typeService.findAll();
    model.addAttribute("courses", courses);
    model.addAttribute("currentPages", "contact");
    model.addAttribute("contact", new Contact());
    return "client/contact";
  }

  @PostMapping("/contact")
	public String createContact(RedirectAttributes redirectAttributes,Model model,Principal principal,@ModelAttribute("contact")Contact lh,HttpServletRequest request)
	{
    if (principal != null) { 
      Users users = userService.findByUsername(principal.getName());
      lh.setDayContact(new Date());
      lh.setUsers(users);
      lh.setStatus("Chưa trả lời");
		  contactService.save(lh);
      redirectAttributes.addAttribute("success", "Sent contact Success!");
      return "redirect:" + request.getHeader("Referer");
    } 
		return "auth/login";
		
	}

    @RequestMapping("/lstcontact")
    public String lst(Model model) {
        model.addAttribute("title", "Manage Contact");
        List<Contact> contacts = contactService.findAll();
        model.addAttribute("courses", contacts);
        model.addAttribute("size", contacts.size());
        //model.addAttribute("usernew", new Users());
        return "admin/lstcontact";
    }

    @RequestMapping(value = "/findByCartId", method = { RequestMethod.PUT, RequestMethod.GET })
    @ResponseBody
    public Optional<Contact> findById(Long id) {
        return contactService.findById(id);
    }

    @PostMapping("/update_cart")
    public String update(Model model,RedirectAttributes redirectAttributes,@RequestParam("id")Long id, @RequestParam("reply")String reply,HttpServletRequest request){
      int status=contactService.update(reply, new Date(), id);
      Contact contact=contactService.findById(id).get();
      emailUlti.sendEmail(contact.getEmail(),contact.getTitle(),reply);
      if(status>0){
        redirectAttributes.addAttribute("success", "Reply Success!");
        return "redirect:" + request.getHeader("Referer");
      }
      
      return "redirect:" + request.getHeader("Referer");
    }


}
