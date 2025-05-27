package com.zwj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teach {

    private int id;
    private String teacherId;
    private String teacherName;

    private String courseName;
    private String courseCode;
    private int begin;
    private int end;
    private int status;

    private String day;
    private String time;

    private int roomId;
    private String roomName;

    private int now;
    private int max;

}
