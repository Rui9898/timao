package com.lr.vo;

import lombok.Data;

@Data
public class CartVO {
    private Integer id;
    private String name;
    private String fileName;
    private Float price;
    private Integer quantity;
    private Float cost;
    private Integer stock;
    private Integer productId;
}
