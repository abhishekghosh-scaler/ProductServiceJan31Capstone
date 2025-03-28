package com.scaler.productservicejan31capstone.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Product extends BaseModel implements Serializable
{
    private String description;
    private String imageUrl;
    private double price;
    @ManyToOne
    private Category category;
}
