package com.zwj.controller;

import cn.hutool.core.util.IdUtil;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.User;
import com.zwj.service.UserServiceImpl;
import com.zwj.utils.Result;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserServiceImpl userService;

    @Resource
    JavaMailSenderImpl mailSender;


    @PostMapping("/add")
    public Result<?> save(@RequestBody User user){
        if (userService.findUserByUsername(user.getUsername()) != null){
            return Result.error("新增失败,该用户名已存在！");
        }
        int res = userService.save(user);
        return res == 1 ? Result.success() : Result.error("未知错误");
    }

    @GetMapping("/list")
    public Result<?> getList(){
        List<User> list = userService.getList();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<?> page(UserPageRequest userPageRequest){
        System.out.println(userPageRequest.getRole());
        return Result.success(userService.page(userPageRequest));
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id){
        int res = userService.delete(id);
        return res == 1 ? Result.success() : Result.error("未知错误");
    }

    @PostMapping("/update/{id}")
    public Result<?> resetPassword(@PathVariable Integer id){
        int res = userService.resetPassword(id);
        return res == 1 ? Result.success() : Result.error("未知错误");
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody User user){
        int res = userService.update(user);
        return res == 1 ? Result.success() : Result.success("未知错误");
    }

    @PostMapping("/updateUser")
    public Result<?> updateByUser(@RequestBody User user){
        int res = userService.updateByUser(user);
        return res == 1 ? Result.success() : Result.success("未知错误");
    }

    @GetMapping("/{id}")
    public Result<?> findUserById(@PathVariable Integer id){
        return Result.success(userService.findUserById(id));
    }

    @GetMapping("/teacher/list")
    public Result<?> getTeacherList(){
        List<User> list = userService.getTeacherList();
        return Result.success(list);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        User res = userService.findUserByUsername(user.getUsername());
        if (res == null){
            return Result.error("用户名不存在");
        } else if (userService.login(user.getUsername(),user.getPassword()) == null){
            return Result.error("密码错误");
        } else {
            res.setPassword("");
            return Result.success(res);
        }
    }

    @PostMapping("/enroll")
    public Result<?> enroll(@RequestBody User user){
        if (userService.findUserByUsername(user.getUsername()) != null){
            return Result.error("注册失败,该用户名已存在！");
        }
        int res = userService.save(user);
        return res == 1 ? Result.success() : Result.error("未知错误");
    }

    @PostMapping("/remind")
    public Result<?> remind(@RequestBody User user){
        User result = userService.findUserByUsername(user.getUsername());
        String email = user.getEmail();
        if (result != null && email.equals(result.getEmail())){
            String code = IdUtil.fastUUID().substring(2,6);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("重置密码提醒");
            message.setText("您正在通过个人信息中的邮箱地址来重置密码，验证码为：" + code + "，请勿泄漏！" );
            message.setFrom("2430305938@qq.com");
            message.setTo(email);
            mailSender.send(message);
            return Result.success(code + result.getUsername());
        }
        return Result.error("请仔细检查邮箱与用户名是否匹配哦~");
    }

    @PostMapping("/reset")
    public Result<?> reset(@RequestBody User user){
        User result = userService.findUserByUsername(user.getUsername());
        if (result != null && user.getEmail().equals(result.getEmail())){
            //说明对方在收到验证码后没有修改用户名和邮箱
            int res = userService.resetPassword(result.getId());
            return res == 1 ? Result.success() : Result.error("未知错误");
        }
        return Result.error("验证码与要重置密码的用户名不匹配哦~");
    }



}
