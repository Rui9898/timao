package com.lr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lr.entity.Product;
import com.lr.mapper.ProductMapper;
import com.lr.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public List<Product> findByLevle(Integer level, Integer levelId) {
        QueryWrapper wrapper=new QueryWrapper();
        switch (level){
            case 1:
                wrapper.eq("categorylevelone_id",levelId);
                break;
            case 2:
                wrapper.eq("categoryleveltwo_id",levelId);
                break;
            case 3:
                wrapper.eq("categorylevelthree_id",levelId);
                break;
        }
        return this.productMapper.selectList(wrapper);
    }
}
