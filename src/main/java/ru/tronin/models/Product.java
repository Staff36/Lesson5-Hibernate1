package ru.tronin.models;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(length = 128, nullable = false)
    String title;
    Float cost;

    public Product(String title, Float cost) {
        this.title = title;
        this.cost = cost;
    }
}

