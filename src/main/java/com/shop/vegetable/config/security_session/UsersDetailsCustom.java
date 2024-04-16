// package com.shop.vegetable.config.security_session;


// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import com.shop.vegetable.entity.Role;
// import com.shop.vegetable.entity.Users;

// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.List;

// public class UsersDetailsCustom implements UserDetails {
//     private Users user;

//     public UsersDetailsCustom(Users user) {
//         this.user = user;
//     }

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//         for(Role role : user.getRoles()){
//             authorities.add(new SimpleGrantedAuthority(role.getName()));
//         }
//         return authorities;
//     }

//     @Override
//     public String getPassword() {
//         return user.getPassword();
//     }

//     @Override
//     public String getUsername() {
//         return user.getUsername();
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }
// }