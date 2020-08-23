package com.lr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lr.entity.*;
import com.lr.mapper.*;
import com.lr.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lr.vo.OrderDetailVO;
import com.lr.vo.OrdersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Integer create(String selectAddress, String address, String remark, User user, Float cost) {
        Orders orders =new Orders();
        orders.setUserId(user.getId());
        orders.setLoginName(user.getLoginName());
        //判断是新地址还是老地址
        if(selectAddress.equals("newAddress")){
            //保存新地址
            UserAddress userAddress = new UserAddress();
            userAddress.setAddress(address);
            userAddress.setRemark(remark);
            userAddress.setUserId(user.getId());
            userAddress.setIsdefault(1);
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("isdefault",1);
            wrapper.eq("user_id",user.getId());
            UserAddress userAddress1 = this.userAddressMapper.selectOne(wrapper);
            userAddress1.setIsdefault(0);
            this.userAddressMapper.updateById(userAddress1);
            this.userAddressMapper.insert(userAddress);
            selectAddress=address;
        }
        orders.setUserAddress(selectAddress);
        orders.setCost(cost);
        String seriaNumber =null;
        try{
            StringBuffer result =new StringBuffer();
            for(int i=0;i<32;i++){
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriaNumber = result.toString().toUpperCase();
        }catch (Exception e){
            e.printStackTrace();
        }
        orders.setSerialnumber(seriaNumber);
        int result = this.ordersMapper.insert(orders);
        if(result == 1){
            return  orders.getId();
        }else{
            return null;
        }
    }

    @Override
    public List<OrdersVO> findVOByUserId(Integer userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<Orders> ordersList= this.ordersMapper.selectList(wrapper);
        //解析成OrdersVO
        List<OrdersVO> ordersVOList = new ArrayList<>();
        for (Orders orders : ordersList){
            OrdersVO ordersVO =new OrdersVO();
            BeanUtils.copyProperties(orders,ordersVO);
            //解析订单详情
            wrapper = new QueryWrapper();
            wrapper.eq("order_id",orders.getId());
            List<OrderDetail> orderDetailList = this.orderDetailMapper.selectList(wrapper);
            //解析成OrderDetailVO
            List<OrderDetailVO> orderDetailVOList = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetailList) {
                OrderDetailVO orderDetailVO = new OrderDetailVO();
                BeanUtils.copyProperties(orderDetail,orderDetailVO);
                Product product = this.productMapper.selectById(orderDetail.getProductId());
                BeanUtils.copyProperties(product,orderDetailVO);
                orderDetailVOList.add(orderDetailVO);
            }
            ordersVO.setOrderDetailVOS(orderDetailVOList);
            ordersVOList.add(ordersVO);
        }
        return ordersVOList;
    }
}
