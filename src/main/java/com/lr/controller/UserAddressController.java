package com.lr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lr.entity.User;
import com.lr.enums.ResultEnum;
import com.lr.exception.TianmaoException;
import com.lr.service.CartService;
import com.lr.service.UserAddressService;
import com.lr.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
@Controller
@RequestMapping("//userAddress")
@Slf4j
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private CartService cartService;

    @GetMapping("/manage")
    public ModelAndView manage(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            log.info("未登录,user={}",user);
            throw new TianmaoException(ResultEnum.NOT_ERROR);
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        modelAndView.addObject("list",this.userAddressService.list(wrapper));
        List<CartVO> cartVOS = this.cartService.findByUserId(user.getId());
        modelAndView.addObject("carts",cartVOS);
        return modelAndView;
    }

}

