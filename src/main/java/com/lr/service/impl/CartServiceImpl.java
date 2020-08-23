package com.lr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lr.entity.Cart;
import com.lr.entity.Product;
import com.lr.mapper.CartMapper;
import com.lr.mapper.ProductMapper;
import com.lr.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lr.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper mapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<CartVO> findByUserId(Integer userId) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<Cart> list=this.mapper.selectList(wrapper);
        List<CartVO> voList=new ArrayList<>();
        for (Cart cart : list) {
            CartVO cartVO=new CartVO();
            Product product=this.productMapper.selectById(cart.getProductId());
            BeanUtils.copyProperties(product,cartVO);
            BeanUtils.copyProperties(cart,cartVO);
            cartVO.setProductId(product.getId());
            voList.add(cartVO);
        }
        return voList;
    }
}
