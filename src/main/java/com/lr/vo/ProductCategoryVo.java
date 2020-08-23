package com.lr.vo;

import com.lr.entity.Product;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class ProductCategoryVo {
    private Integer id;
    private String name;
    private List<ProductCategoryVo> children;
    private String bannerImg;
    private String topImg;
    private List<Product> products;

}
