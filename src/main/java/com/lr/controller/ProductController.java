package com.lr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lr.entity.Product;
import com.lr.entity.User;
import com.lr.service.CartService;
import com.lr.service.ProductCategoryService;
import com.lr.service.ProductService;
import com.lr.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
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
@RequestMapping("//product")
public class ProductController {

    @Autowired

    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CartService cartService;

    @GetMapping("/findByCategory/{type}/{id}")
    public ModelAndView findByCategory(@PathVariable("type") Integer tpye, @PathVariable("id") Integer id,
                                       HttpSession session){
        //根据类型和Id取值
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("productList");
        modelAndView.addObject("products",this.productService.findByLevle(tpye,id));
        modelAndView.addObject("list",this.productCategoryService.findAll());
        User user=(User) session.getAttribute("user");
        List<CartVO> carts=new ArrayList<>();
        if(user !=null){
            carts=this.cartService.findByUserId(user.getId());
        }
        modelAndView.addObject("carts",carts);
       return  modelAndView;
    }

    @PostMapping("/findByKey")
    public ModelAndView findByKey(String keyWord,HttpSession session){
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.like("name",keyWord);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("productList");
        modelAndView.addObject("products",this.productService.list(wrapper));
        modelAndView.addObject("list",this.productCategoryService.findAll());
        User user=(User) session.getAttribute("user");
        List<CartVO> carts=new ArrayList<>();
        if(user !=null){
            carts=this.cartService.findByUserId(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }

    @GetMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable("id")Integer id,HttpSession session ){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("product",this.productService.getById(id));
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

