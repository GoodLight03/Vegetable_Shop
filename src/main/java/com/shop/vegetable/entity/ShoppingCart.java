package com.shop.vegetable.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@AllArgsConstructor
// @NoArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", referencedColumnName = "users_id")
    private Users users;

    private double totalPrice;

    private int totalItems;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "cart")
    private Set<CartItem> cartItems;

    public ShoppingCart() {
        this.cartItems = new HashSet<>();
        this.totalItems = 0;
        this.totalPrice = 0.0;
    }

    // @Override
    // public String toString() {
    //     return "ShoppingCart{" +
    //             "id=" + id +
    //             ", customer=" + users.getUsername() +
    //             ", totalPrice=" + totalPrice +
    //             ", totalItems=" + totalItems +
    //             ", cartItems=" + users.size() +
    //             '}';
    // }
}
