package com.scaler.productservicejan31capstone.repositories;

public class CustomQuery
{
    public static final String GET_PRODUCTS_FROM_CATEGORY_NAME =
            "select * from product where category_id in (select category_id from category where name = :categoryName)";
}
