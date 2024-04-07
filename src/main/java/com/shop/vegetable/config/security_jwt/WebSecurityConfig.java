package com.shop.vegetable.config.security_jwt;
// package com.shop.vegetable.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.shop.vegetable.config.jwt.AuthEntryPointJwt;
// import com.shop.vegetable.config.jwt.AuthTokenFilter;
// import com.shop.vegetable.config.service.UserDetailsServiceImpl;

// @SuppressWarnings("deprecation")
// @Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class WebSecurityConfig {
//   @Autowired
//   private UserDetailsServiceImpl userDetailsService;

//   @Autowired
//   private AuthEntryPointJwt unauthorizedHandler;

//   @Bean
//   public AuthTokenFilter authenticationJwtTokenFilter() {
//     return new AuthTokenFilter();
//   }

//   @Bean
//   public DaoAuthenticationProvider authenticationProvider() {
//     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

//     authProvider.setUserDetailsService(userDetailsService);
//     authProvider.setPasswordEncoder(passwordEncoder());

//     return authProvider;
//   }

//   @Bean
//   public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//     return authConfig.getAuthenticationManager();
//   }

//   @Bean
//   public PasswordEncoder passwordEncoder() {
//     return new BCryptPasswordEncoder();
//   }

//   @Bean
//   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//     // http.cors().and().csrf().disable()
//     //     .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//     //     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//     //     .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//     //     .antMatchers("/**").permitAll()
//     //     .anyRequest().authenticated();

//     http.csrf().disable()
//         .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//         .and()
//         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//         .authorizeRequests()
//         // Các cấu hình permitAll() và hasAuthority() khác
//         .requestMatchers("/api/auth/**").permitAll()
//         .requestMatchers("/user", "/type", "/eclass", "/product", "/admin","/lstcontact").hasAuthority("ADMIN");
//         // .anyRequest().authenticated()
//         // .requestMatchers("/", "/forgot-password", "/register", "/register-new","/shop").permitAll()
      

//     // http.csrf(AbstractHttpConfigurer::disable)
//     //     .authorizeHttpRequests(author -> author
//     //         .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//     //         .permitAll()
//     //         // .requestMatchers("/home").permitAll()
//     //         .requestMatchers("/carts", "/check-out").hasAuthority("CUSTOMER")
//     //         .requestMatchers("/", "/forgot-password", "/register", "/register-new",
//     //             "/shop")
//     //         .permitAll()
//     //         .requestMatchers("/user", "/type", "/eclass", "/product", "/admin", "/lstcontact")
//     //         .hasAuthority("ADMIN")
//     //         .anyRequest().permitAll()
//     //     // .anyRequest().authenticated()

//     //     ).exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//     //     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//     http.authenticationProvider(authenticationProvider());

//     http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

//     return http.build();
//   }

// }
