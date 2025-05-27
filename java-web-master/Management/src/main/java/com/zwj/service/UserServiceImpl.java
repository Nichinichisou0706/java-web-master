package com.zwj.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.mapper.UserMapper;
import com.zwj.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserMapper userMapper;

    @Override
    public int save(User user) {
        if (user.getPassword() == null){
            user.setPassword(DigestUtil.md5Hex("123456"));
        } else {
            user.setPassword(DigestUtil.md5Hex(user.getPassword()));
            user.setRole("用户");
        }
        return userMapper.save(user);
    }

    @Override
    public List<User> getList() {
        return userMapper.list();
    }

    @Override
    public Object page(UserPageRequest userPageRequest) {
        PageHelper.startPage(userPageRequest.getPageNum(),userPageRequest.getPageSize());
        List<User> users = userMapper.listByCondition(userPageRequest);
        return new PageInfo<>(users);
    }

    @Override
    public int delete(int id) {
        return userMapper.delete(id);
    }

    @Override
    public int resetPassword(int id) {
        String s = DigestUtil.md5Hex("123456");
        return userMapper.resetPassword(id,s);
    }

    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public User login(String username, String password) {
        password = DigestUtil.md5Hex(password);
        return userMapper.login(username,password);
    }

    @Override
    public int update(User user) {
        user.setUpdateTime(new Date());
        return userMapper.update(user);
    }

    @Override
    public int updateByUser(User user) {
        user.setUpdateTime(new Date());
        return userMapper.updateByUser(user);
    }

    @Override
    public List<User> getTeacherList() {
        return userMapper.getTeacherList();
    }

}
