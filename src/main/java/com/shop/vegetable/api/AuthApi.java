// package com.shop.vegetable.api;


// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseCookie;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;


// import com.shop.vegetable.dto.reponse.MessageResponse;
// import com.shop.vegetable.dto.reponse.TokenResponse;
// import com.shop.vegetable.dto.request.LoginRequest;
// import com.shop.vegetable.dto.request.RegisterRequest;
// import com.shop.vegetable.service.UserService;
// import com.shop.vegetable.service.impl.AccountServiceImpl;

// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthApi {
//     @Autowired
//     private AccountServiceImpl accountService;

//     @PostMapping("login")
//     public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
//         return new ResponseEntity<>(accountService.logink(loginRequest), HttpStatus.OK);
//     }

//     @PostMapping("register")
//     public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest registerRequest) {
//         return new ResponseEntity<>(accountService.register(registerRequest), HttpStatus.CREATED);
//     }

//     // @PostMapping("/logout")
//     // public ResponseEntity<?> logoutUser() {
//     //   ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//     //   return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//     //       .body(new MessageResponse("You've been logout!"));
//     // }
// }
