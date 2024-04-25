package com.shop.vegetable.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class UsersConfiguration {

        @Autowired
	private AuthenticationSuccessHandler successHandler;

        @Bean
        public UserDetailsService userDetailsService() {
                return new UsersServiceConfig();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);

                authenticationManagerBuilder
                                .userDetailsService(userDetailsService())
                                .passwordEncoder(passwordEncoder());

                AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

                http
                        
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(author -> author
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll()
                                        // .requestMatchers("/home").permitAll()
                                        .requestMatchers("/carts", "/check-out").hasAuthority("CUSTOMER")
                                        .requestMatchers("/", "/forgot-password", "/register", "/register-new",
                                                                 "/shop")
                                        .permitAll()
                                        .requestMatchers("/user", "/type", "/eclass", "/product", "/admin","/lstcontact")
                                        .hasAuthority("ADMIN")
                                        .requestMatchers("/shipper")
                                        .hasAuthority("SHIPPER")
                                        .anyRequest().permitAll()
                                // .anyRequest().authenticated()

                        )
                        .formLogin(login -> login.loginPage("/login")
                                        .loginProcessingUrl("/do-login")
                                        .successHandler(successHandler)
                                        //.defaultSuccessUrl("/", true)
                                        .permitAll())
                        .logout(logout -> logout.invalidateHttpSession(true)
                                        .clearAuthentication(true)
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                        .logoutSuccessUrl("/login?logout")
                                        .permitAll())
                                        
                        .exceptionHandling(exceptionHandling -> exceptionHandling
                                        .accessDeniedPage("/errors")
                                         // Trang view mặc định cho quyền
                                                                                // truy cập bị từ chối
                        )
                        .rememberMe(key->key
                                .key("uniqueAndSecret").rememberMeParameter("remember-me")
                        )
                        .authenticationManager(authenticationManager)
                        .sessionManagement(
                                        session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
                return http.build();
        }

        public UserDetails loadUserByUsername(String username) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
        }

}
