package com.lr.service;

import com.lr.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lr.vo.ProductCategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
public interface ProductService extends IService<Product> {

    public List<Product> findByLevle(Integer level,Integer levelId);
}
