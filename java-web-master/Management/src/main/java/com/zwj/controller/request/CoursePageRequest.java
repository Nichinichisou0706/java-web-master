package com.zwj.controller.request;

import lombok.Data;


@Data
public class CoursePageRequest extends BaseRequest {

    private String courseName;
    private String courseCode;

}
