package com.lr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lr.entity.Cart;
import com.lr.entity.OrderDetail;
import com.lr.mapper.CartMapper;
import com.lr.mapper.OrderDetailMapper;
import com.lr.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<OrderDetail> gerByUserId(Integer userId,Integer orderId) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<Cart> carts=this.cartMapper.selectList(wrapper);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Cart cart:carts){
            OrderDetail orderDetail= new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetails.add(orderDetail);
        }
        return  orderDetails;
    }
}
