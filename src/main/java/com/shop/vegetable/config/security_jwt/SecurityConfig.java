// package com.shop.vegetable.config.security_jwt;


// import lombok.RequiredArgsConstructor;

// import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.shop.vegetable.utils.JwtAuthenticationFilter;

// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
// public class SecurityConfig {

//     private final AuthenticationProvider authenticationProvider;

//     private final JwtAuthenticationFilter jwtAuthenticationFilter;

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//         httpSecurity
//                 .csrf(AbstractHttpConfigurer::disable)
//                 .authorizeHttpRequests(auth -> auth
//                 .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                 .permitAll()
//                 // .requestMatchers("/home").permitAll()
//                 .requestMatchers("/carts", "/check-out").hasAuthority("CUSTOMER")
//                 .requestMatchers("/", "/forgot-password", "/register", "/register-new",
//                                          "/shop")
//                 .permitAll()
//                 .requestMatchers("/user", "/type", "/eclass", "/product", "/admin","/lstcontact")
//                 .hasAuthority("ADMIN")
//                 .anyRequest().permitAll())
//                 .sessionManagement(
//                         sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authenticationProvider(authenticationProvider)
//                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//         return httpSecurity.build();
//     }

// }
