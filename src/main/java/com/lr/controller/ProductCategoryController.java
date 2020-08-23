package com.lr.controller;


import com.lr.entity.User;
import com.lr.service.CartService;
import com.lr.service.ProductCategoryService;
import com.lr.service.impl.ProductCategoryServiceImpl;
import com.lr.vo.CartVO;
import com.lr.vo.ProductCategoryVo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
@Controller
@RequestMapping("//productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CartService cartService;

    @GetMapping("/findAll")
    public ModelAndView findAll(HttpSession session){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("main");
        modelAndView.addObject("list",this.productCategoryService.findAll());
        User user=(User) session.getAttribute("user");
        List<CartVO> carts=new ArrayList<>();
        if(user !=null){
            carts=this.cartService.findByUserId(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }
}

