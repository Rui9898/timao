package com.lr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lr.entity.User;
import com.lr.mapper.UserMapper;
import com.lr.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("login_name",user.getLoginName());
        queryWrapper.eq("password",user.getPassword());
        return this.userMapper.selectOne(queryWrapper);
    }
}
