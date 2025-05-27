package com.zwj.mapper;

import com.zwj.controller.request.CoursePageRequest;
import com.zwj.pojo.Course;
import com.zwj.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper {

    List<Course> list();

    List<Course> listByCondition(CoursePageRequest pageRequest);

    int delete(int id);

    int save(Course course);

    int update(Course course);

    int changeStatus(int id, int status);

    Course getCourseById(int id);

    Course getCourseByCourseCode(String courseCode);

}
