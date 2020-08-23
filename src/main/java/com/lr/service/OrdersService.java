package com.lr.service;

import com.lr.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lr.entity.User;
import com.lr.vo.OrdersVO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
public interface OrdersService extends IService<Orders> {
    public Integer create(String selectAddress, String address , String remark , User user, Float cost);
    public List<OrdersVO> findVOByUserId(Integer userId);
}
