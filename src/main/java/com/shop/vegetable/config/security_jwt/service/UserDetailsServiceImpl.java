package com.shop.vegetable.config.security_jwt.service;
// package com.shop.vegetable.config.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;


// import com.shop.vegetable.entity.Users;
// import com.shop.vegetable.repository.UsersRepository;

// import jakarta.transaction.Transactional;




// @Service
// public class UserDetailsServiceImpl implements UserDetailsService {

//     @Autowired
//     private UsersRepository userRepository;

//     @Override
//     @Transactional
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         // TODO Auto-generated method stub
//         Users user = userRepository.findByUsername(username);
//        // .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//        if (user == null) {
//                     throw new UsernameNotFoundException("Could not find username");
//         }

//         // if(user != null && user.isEnabled()){
            
//         //     return UserDetailsImpl.build(user);
//         // }else{
//         //     throw new UsernameNotFoundException("User Not Found with username: " + username);
//         // }

//         return UserDetailsImpl.build(user);

        
//     }
    
// }