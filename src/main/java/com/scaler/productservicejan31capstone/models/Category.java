package com.scaler.productservicejan31capstone.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel
{
    private String description;

    @OneToMany(mappedBy = "category")
    @Fetch(FetchMode.JOIN)
    private List<Product> products;
}
