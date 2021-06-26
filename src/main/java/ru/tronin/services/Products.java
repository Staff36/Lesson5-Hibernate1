package ru.tronin.services;

import lombok.Data;
import ru.tronin.models.Product;

import java.util.List;

@Data
public class Products {

    private final List<Product> productList;

    public Products() {
        this.productList = List.of(
                new Product("Apple", 10.99f),
                new Product("Orange", 8.99f),
                new Product("Tomato", 3.92f),
                new Product("Cucumber", 1.83f),
                new Product("Cherry", 4.00f));
    }
}
