package com.zwj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private int id;
    private String courseName;
    private String courseCode;
    private int begin;
    private int end;
    private int status;

}
