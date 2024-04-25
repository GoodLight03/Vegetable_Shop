package com.shop.vegetable.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products"
// , uniqueConstraints = @UniqueConstraint(columnNames = { "name" })
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String description;
    private float price;
    private String status="Enable";
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    //@JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private Type type;

    // @JsonBackReference
    // //@JsonIgnore
    // @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    // private List<OrderDetail> orderDetailList;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<CartItem> cartiterms;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Comment> comments;

    // @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    // private OrderDetail orderDetail;
}
