package com.lr.service;

import com.lr.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lr.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
public interface CartService extends IService<Cart> {
    public List<CartVO> findByUserId(Integer userId );
}
