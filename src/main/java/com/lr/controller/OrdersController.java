package com.lr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.lr.entity.OrderDetail;
import com.lr.entity.Orders;
import com.lr.entity.User;
import com.lr.entity.UserAddress;
import com.lr.enums.ResultEnum;
import com.lr.exception.TianmaoException;
import com.lr.service.CartService;
import com.lr.service.OrderDetailService;
import com.lr.service.OrdersService;
import com.lr.service.UserAddressService;
import com.lr.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
@Controller
@RequestMapping("//orders")
@Slf4j
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CartService cartService;


    /**
     * 创建订单
     * @return
     */
    @PostMapping("/create")
    public String create(String selectAddress, String address ,String remark ,HttpSession session ,Float cost){
        //存入订单
        User user =(User) session.getAttribute("user");
        if(user==null){
            log.info("未登录。user={}",user);
            throw new TianmaoException(ResultEnum.NOT_ERROR);
        }
        Integer ordersId = this.ordersService.create(selectAddress, address, remark, user, cost);
        if(ordersId == null){
            log.info("生成订单失败");
            throw new TianmaoException(ResultEnum.ORDER_CREATE_FALL);
        }
        //存入订单详情
        List<OrderDetail> orderDetails=this.orderDetailService.gerByUserId(user.getId(),ordersId);
        this.orderDetailService.saveBatch(orderDetails);
        //删除购物车
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        this.cartService.remove(wrapper);
        //跳转到settlement3
        return "redirect:/cart/goToSettlement3/"+ordersId+"/"+user.getId();
    }

    @GetMapping("/ordersList")
    public ModelAndView ordersList(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            log.info("未登录,user={}",user);
            throw new TianmaoException(ResultEnum.NOT_ERROR);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        List<CartVO> cartVOS = this.cartService.findByUserId(user.getId());
        modelAndView.addObject("carts",cartVOS);
        modelAndView.addObject("list",this.ordersService.findVOByUserId(user.getId()));
        return  modelAndView;
    }
}

