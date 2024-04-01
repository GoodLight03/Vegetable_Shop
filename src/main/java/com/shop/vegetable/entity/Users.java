package com.shop.vegetable.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userss"
// ,uniqueConstraints = @UniqueConstraint(columnNames = "username")
)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;
    private String username;
    private String password;
    private String address;
    private String phone;
    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    @JsonBackReference
    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private ShoppingCart cart;
    @JsonBackReference
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Order> orders;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Comment> comments;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Contact> contacts;

}
