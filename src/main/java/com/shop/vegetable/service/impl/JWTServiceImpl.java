// package com.shop.vegetable.service.impl;


// import java.security.Key;
// import java.util.Date;


// import org.springframework.security.core.userdetails.UserDetails;

// import org.springframework.stereotype.Service;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.io.Decoder;
// import io.jsonwebtoken.io.Decoders;

// @Service
// public class JWTServiceImpl {
//     private String generateToken(UserDetails userDetails){
//         return Jwts.builder().setSubject(userDetails.getUsername())
//         .setIssuedAt(new Date(System.currentTimeMillis()))
//         .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
//         .signWith(get)
//     }

//     private Key getSiginKey(){
//         byte[] key=Decoders.BASE64.decode(null)
//     }
// }
