package com.lr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.lr.entity.Cart;
import com.lr.entity.Product;
import com.lr.entity.User;
import com.lr.entity.UserAddress;
import com.lr.enums.ResultEnum;
import com.lr.exception.TianmaoException;
import com.lr.service.CartService;
import com.lr.service.OrdersService;
import com.lr.service.ProductService;
import com.lr.service.UserAddressService;
import com.lr.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("//cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/goToSettlement3/{ordersId}/{userId}")
    public ModelAndView goToSettlement3(@PathVariable("ordersId") Integer ordersId,
                                        @PathVariable("userId") Integer userId){
        ModelAndView modelAndView=new ModelAndView();
        List<CartVO> cartVOS = this.cartService.findByUserId(userId);
        modelAndView.setViewName("settlement3");
        modelAndView.addObject("orders",this.ordersService.getById(ordersId));
        modelAndView.addObject("carts",cartVOS);
        return modelAndView;
    }

    @GetMapping("/goToSettlement2")
    public ModelAndView goToSettlement2(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            log.info("未登录,user={}",user);
            throw new TianmaoException(ResultEnum.NOT_ERROR);
        }
        //查询该用户的购物车记录
        List<CartVO> cartVOS = this.cartService.findByUserId(user.getId());
        //查询用户对应的地址
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        List<UserAddress> userAddressList = this.userAddressService.list(wrapper);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("settlement2");
        modelAndView.addObject("carts",cartVOS);
        modelAndView.addObject("address",userAddressList);
        return  modelAndView;
    }
//@GetMapping("/goToSettlement2")
//public ModelAndView goToSettlement2(HttpSession session){
//    User user = (User) session.getAttribute("user");
//    if(user == null){
//        log.info("未登录，user={}", user);
//        throw new MallException(ResultEnum.NOT_LOGIN);
//    }
//    //查询该用户的购物车记录
//    List<CartVO> cartVOS = this.cartService.findByUserId(user.getId());
//    //查询用户对应的地址
//    QueryWrapper wrapper = new QueryWrapper();
//    wrapper.eq("user_id", user.getId());
//    List<UserAddress> userAddressList = this.userAddressService.list(wrapper);
//    ModelAndView modelAndView = new ModelAndView();
//    modelAndView.setViewName("settlement2");
//    modelAndView.addObject("carts",cartVOS);
//    modelAndView.addObject("address",userAddressList);
//    return modelAndView;
//}
    /**
     * 跳转到购物车页面
     * @param session
     * @return
     */
    @GetMapping("/goToSettlement1")
    public ModelAndView goToSettlement1(HttpSession session){
        User user =(User) session.getAttribute("user");
        if(user==null){
            throw new TianmaoException(ResultEnum.NOT_ERROR);
        }
        List<CartVO> carts=this.cartService.findByUserId(user.getId());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("settlement1");
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }

    /**
     * 添加购物车
     * @param id
     * @param price
     * @param quantity
     * @param session
     * @return
     */
    @GetMapping("/add/{id}/{price}/{quantity}")
    public String add(@PathVariable("id")Integer id,
                            @PathVariable("price") Float price,
                            @PathVariable("quantity") Integer quantity,
                            HttpSession session){
        try {
        User user =(User) session.getAttribute("user");
        if(user==null){
            throw new TianmaoException(ResultEnum.NOT_ERROR);
        }
        Cart cart=new Cart(id,quantity,price*quantity,user.getId());
        //存入购物车
            boolean save = this.cartService.save(cart);
            if(!save){
                throw new TianmaoException(ResultEnum.CART_SAVE_ERROR);
            }
            //减库存
            Product product=this.productService.getById(id);
            if(product==null){
                log.info("商品不存在 product={}",product);
                throw new TianmaoException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result =product.getStock()-quantity;
            if(result<0){
                log.info("库存不足，stock={},quantity={}",product.getStock(),quantity);
                throw new TianmaoException(ResultEnum.STOCK_ERROR);
            }
            product.setStock(result);
            //存入购物车
            boolean update = this.productService.updateById(product);
            if(!update){
                log.info("保存失败，product={}",product);
                throw new TianmaoException(ResultEnum.STOCK_UPDATE_ERROR);
            }
            return "redirect:/cart/goToSettlement1";
        } catch (Exception e) {
            throw new TianmaoException(ResultEnum.CART_ERROR);

        }
    }

    /***
     * 更新购物车
     * @param id
     * @param quantity
     * @param cost
     * @return
     */
    @PostMapping("/updateCart/{type}/{id}/{productId}/{quantity}/{cost}")
    @ResponseBody
    public String updateCart(@PathVariable("type") String type,
                             @PathVariable("id") Integer id,
                             @PathVariable("productId") Integer productId,
                             @PathVariable("quantity") Integer quantity,
                             @PathVariable("cost") Float cost){
        Cart cart = this.cartService.getById(id);
        cart.setQuantity(quantity);
        cart.setCost(cost);
        boolean update= this.cartService.updateById(cart);
        //更新库存
        Product product=this.productService.getById(productId);
        if(product==null){
            log.info("商品不存在 product={}",product);
            throw new TianmaoException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        Integer result =null;
        switch (type){
            case "add":
                result=product.getStock()-1;
                break;
            case "sub":
                result=product.getStock()+1;
                break;
        }
        if(result < 0){
            log.info("库存不足，stock={},quantity={}",product.getStock(),quantity);
            throw new TianmaoException(ResultEnum.STOCK_ERROR);
        }
        product.setStock(result);
        update = this.productService.updateById(product);
        if(update){
            return "success";
        }else{
            return "fail";
        }
    }

    @GetMapping("/removeCart/{id}")
    public String removeCart(@PathVariable("id") Integer id){
        if(id==null){
            throw new TianmaoException(ResultEnum.ID_IS_NULL);
        }
        Cart cart=this.cartService.getById(id);
        if(cart==null  ){
            throw new TianmaoException(ResultEnum.CART_IS_NULL);
        }
        this.cartService.removeById(id);
        return "redirect:/cart/goToSettlement1";
    }

}

