package com.jonasramos.course01.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "product")
@Entity(name = "product")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Boolean active;

    public Product(RequestProduct requestProduct) {
        this.name = requestProduct.name();
        this.price = requestProduct.price();
        this.active = true;
    }
}
