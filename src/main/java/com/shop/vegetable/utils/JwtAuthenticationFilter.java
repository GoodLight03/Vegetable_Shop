// package com.shop.vegetable.utils;


// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.validation.constraints.NotNull;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.shop.vegetable.config.security_session.UsersDetails;

// import java.io.IOException;

// @Component
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtService jwtService;

//     private final UserDetailsService userDetailService;

//     @Override
//     protected void doFilterInternal(
//             @NotNull HttpServletRequest request,
//             @NotNull HttpServletResponse response,
//             @NotNull FilterChain filterChain) throws ServletException, IOException {
//         final String authHeader = request.getHeader("Authorization");
//         final String jwt;
//         final String username;
//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             filterChain.doFilter(request, response);
//             return;
//         }
//         jwt = authHeader.substring(7);
//         username = jwtService.extractUsername(jwt);
//         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//             UsersDetails userCustomDetail = (UsersDetails) this.userDetailService.loadUserByUsername(username);
//             if (jwtService.isTokenValid(jwt, userCustomDetail)) {
//                 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                         userCustomDetail,
//                         null,
//                         userCustomDetail.getAuthorities()
//                 );
//                 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//             }
//         }
//         filterChain.doFilter(request, response);
//     }
// }
