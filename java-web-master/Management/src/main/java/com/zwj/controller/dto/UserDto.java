package com.zwj.controller.dto;

import com.zwj.pojo.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private int id;
    private String username;
    private String name;
    private String sex;
    private String phone;
    private String email;
    private String cover;
    private List<Course> courses;

}
