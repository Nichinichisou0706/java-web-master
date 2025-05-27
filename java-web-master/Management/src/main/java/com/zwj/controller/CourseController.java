package com.zwj.controller;

import com.zwj.controller.request.CoursePageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Course;
import com.zwj.service.CourseServiceImpl;
import com.zwj.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    CourseServiceImpl courseService;

    @PostMapping("/add")
    public Result<?> save(@RequestBody Course course){
        if (courseService.getCourseByCourseCode(course.getCourseCode()) != null){
            return Result.error("新增失败,该景点代码已存在！");
        }
        int res = courseService.save(course);
        return res == 1 ? Result.success() : Result.error("未知错误");
    }

    @GetMapping("/list")
    public Result<?> getList(){
        List<Course> list = courseService.list();
        return Result.success(list);
    }

    @GetMapping("/{id}")
    public Result<?> findCourseById(@PathVariable Integer id){
        return Result.success(courseService.getCourseById(id));
    }

    @PutMapping("/change/{id}")
    public Result<?> changeStatus(@PathVariable Integer id){
        int res = courseService.changeStatus(id);
        return res == 1 ? Result.success() : Result.error("未知错误");
    }

    @GetMapping("/page")
    public Result<?> page(CoursePageRequest pageRequest){
        return Result.success(courseService.page(pageRequest));
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id){
        int res = courseService.delete(id);
        return res == 1 ? Result.success() : Result.error("未知错误");
    }


    @PostMapping("/update")
    public Result<?> update(@RequestBody Course course){
        if (courseService.getCourseByCourseCode(course.getCourseCode()).getId() != course.getId()){
            return Result.error("该景点代码已存在，请重新编辑~");
        }
        int res = courseService.update(course);
        return res == 1 ? Result.success() : Result.success("未知错误");
    }


}
