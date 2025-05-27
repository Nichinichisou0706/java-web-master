package com.zwj.controller;


import com.zwj.controller.request.TeachPageRequest;
import com.zwj.mapper.RoomMapper;
import com.zwj.pojo.Room;
import com.zwj.pojo.Teach;
import com.zwj.service.TeachServiceImpl;
import com.zwj.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/teach")
public class TeachController {

    @Resource
    TeachServiceImpl teachService;

    @Resource
    RoomMapper roomMapper;

    @PostMapping("/add")
    public Result<?> save(@RequestBody Teach teach){
        if (teachService.isRight(teach).size() == 0){
            int res = teachService.save(teach);
            return res == 1 ? Result.success() : Result.error("未知错误");
        } else {
            return Result.error("已经有其他景点安排在此地点了哦~");
        }
    }

    @GetMapping("/list")
    public Result<?> getList(){
        List<Teach> list = teachService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<?> page(TeachPageRequest pageRequest){
        return Result.success(teachService.listByCondition(pageRequest));
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id){
        int res = teachService.delete(id);
        return res == 1 ? Result.success() : Result.error("未知错误");
    }

    @GetMapping("/room/list")
    public Result<?> getRoomList(){
        List<Room> list = roomMapper.getList();
        return Result.success(list);
    }

}
