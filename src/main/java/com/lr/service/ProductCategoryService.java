package com.lr.service;

import com.lr.entity.ProductCategory;
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
public interface ProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVo> findAll();
}
