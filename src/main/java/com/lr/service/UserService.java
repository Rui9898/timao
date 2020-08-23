package com.lr.service;

import com.lr.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
public interface UserService extends IService<User> {

    public User login(User user);
}
