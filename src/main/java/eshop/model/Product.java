package eshop.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private int id;
    private String name;
    private String description;
    private Double price;
    private int qty;
    private Category category;

}
