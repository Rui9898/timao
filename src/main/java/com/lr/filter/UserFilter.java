package com.lr.filter;

import com.lr.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;
        HttpSession session=request.getSession();
        User user =(User) session.getAttribute("user");
        //未登录
        if(user==null){
            response.sendRedirect("/login");
        }else{
            //登陆成功，放行
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
