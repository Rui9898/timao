package com.lr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lr.entity.User;
import com.lr.enums.GenderEnum;
import com.lr.enums.ResultEnum;
import com.lr.exception.TianmaoException;
import com.lr.service.CartService;
import com.lr.service.UserService;
import com.lr.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("//user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @PostMapping("/login")
    public String login(User user,HttpSession session){
        User result = this.userService.login(user);
        String url="";
        if(result==null){
            url="login";
        }else{
            session.setAttribute("user",result);
            url="redirect:/";
        }
        return url;
    }

    @PostMapping("/register")
    public String register(User user){
        if(user.getSex() == 0){
            user.setGender(GenderEnum.FEMALE);
        }
        if(user.getSex() == 1){
            user.setGender(GenderEnum.MALE);
        }
        boolean save = this.userService.save(user);
        if(save){
            return "login";
        }else{
            throw new TianmaoException(ResultEnum.REGISTER_ERROR);
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            log.info("未登录,user={}",user);
            throw new TianmaoException(ResultEnum.NOT_ERROR);
        }
        ModelAndView modelAndView=new ModelAndView();
        List<CartVO> cartVOS = this.cartService.findByUserId(user.getId());
        modelAndView.setViewName("userInfo");
        modelAndView.addObject("carts",cartVOS);
        return modelAndView;
    }

}

