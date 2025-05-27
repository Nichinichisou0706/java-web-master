package com.zwj.controller.request;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 朱文杰
 * @Create 2023/3/10 1:27
 */
@Data
public class SearchPageRequest extends BaseRequest{

    private String username;
    private String courseName;
    private String courseCode;

}
