package com.zwj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {

    private int id;
    private int score;

    private String studentId;
    private String studentName;

    private int teachId;
    private String day;
    private String time;
    private int now;
    private int max;

    private int roomId;
    private String roomName;

    private String courseCode;
    private String courseName;
    private int begin;
    private int end;

    private String teacherId;
    private String teacherName;

}
