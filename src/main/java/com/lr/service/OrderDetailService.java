package com.lr.service;

import com.lr.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
public interface OrderDetailService extends IService<OrderDetail> {

    public List<OrderDetail> gerByUserId(Integer userId,Integer orderId);
}