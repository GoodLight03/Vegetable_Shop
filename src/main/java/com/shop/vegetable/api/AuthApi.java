// package com.shop.vegetable.api;


// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
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

// import com.shop.vegetable.config.jwt.JwtUtils;
// import com.shop.vegetable.config.service.UserDetailsImpl;
// import com.shop.vegetable.dto.LoginRequest;
// import com.shop.vegetable.dto.response.MessageResponse;
// import com.shop.vegetable.dto.response.UserInfoResponse;
// import com.shop.vegetable.service.UserService;

// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthApi {
//     @Autowired
//     private AuthenticationManager authenticationManager;

//     @Autowired
//     private JwtUtils jwtUtils;

//     @Autowired
//     private UserService userService;

//     @PostMapping("/login")
//     public ResponseEntity<?> login(@Valid @ModelAttribute LoginRequest loginRequest) {
//         Authentication authentication = authenticationManager
//                 .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
//                         loginRequest.getPassword()));

//         SecurityContextHolder.getContext().setAuthentication(authentication);

//         UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

//         ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

//         List<String> roles = userDetails.getAuthorities().stream()
//                 .map(item -> item.getAuthority())
//                 .collect(Collectors.toList());

//         return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                 .body(new UserInfoResponse(userDetails.getId(),
//                         userDetails.getUsername(),
//                         // userDetails.getEmail(),
//                         roles));
//         // return ResponseEntity.ok(jwtCookie);
//     }

//     // @PostMapping("/register")
//     // public ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest request){
      
//     //     userService.register(request);
      
//     //     return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//     // }

//     @PostMapping("/logout")
//     public ResponseEntity<?> logoutUser() {
//       ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//       return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//           .body(new MessageResponse("You've been logout!"));
//     }
// }
