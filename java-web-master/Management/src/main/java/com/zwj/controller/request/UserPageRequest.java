package com.zwj.controller.request;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 朱文杰
 * @create 2022/11/7 1:26
 */
@Data
public class UserPageRequest extends BaseRequest{

    private String username;
    private String name;
    private String phone;
    private String role;

}
