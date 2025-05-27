package com.zwj.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.controller.request.CoursePageRequest;
import com.zwj.mapper.CourseMapper;
import com.zwj.pojo.Course;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    CourseMapper courseMapper;

    @Override
    public List<Course> list() {
        return courseMapper.list();
    }

    @Override
    public Object page(CoursePageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<Course> courses = courseMapper.listByCondition(pageRequest);
        return new PageInfo<>(courses);
    }

    @Override
    public int delete(int id) {
        return courseMapper.delete(id);
    }

    @Override
    public int save(Course course) {
        return courseMapper.save(course);
    }

    @Override
    public int update(Course course) {
        return courseMapper.update(course);
    }

    @Override
    public int changeStatus(int id) {
        Course course = courseMapper.getCourseById(id);
        int status = (course.getStatus() + 1) % 2;
        return courseMapper.changeStatus(id, status);
    }

    @Override
    public Course getCourseByCourseCode(String courseCode) {
        return courseMapper.getCourseByCourseCode(courseCode);
    }

    @Override
    public Course getCourseById(int id) {
        return courseMapper.getCourseById(id);
    }
}
