package com.zwj.service;

import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.User;

import java.util.List;


public interface UserService {

    int save(User user);

    List<User> getList();

    Object page(UserPageRequest userPageRequest);

    int delete(int id);

    int resetPassword(int id);

    User findUserById(int id);

    User findUserByUsername(String username);

    User login(String username, String password);

    int update(User user);

    int updateByUser(User user);

    List<User> getTeacherList();

}
