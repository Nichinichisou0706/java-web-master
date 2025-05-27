package com.zwj.service;

import com.zwj.controller.request.CoursePageRequest;
import com.zwj.pojo.Course;

import java.util.List;


public interface CourseService {

    List<Course> list();

    Object page(CoursePageRequest pageRequest);

    int delete(int id);

    int save(Course course);

    int update(Course course);

    int changeStatus(int id);

    Course getCourseByCourseCode(String courseCode);

    Course getCourseById(int id);

}
