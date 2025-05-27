package com.zwj.mapper;

import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper {

    List<User> list();

    List<User> listByCondition(UserPageRequest pageRequest);

    int delete(int id);

    User findUserById(int id);

    User findUserByUsername(String username);

    User login(String username, String password);

    int resetPassword(int id, String password);

    int save(User user);

    int update(User user);

    int updateByUser(User user);

    List<User> getTeacherList();

}
