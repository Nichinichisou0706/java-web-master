package com.zwj.controller.request;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 朱文杰
 * @Create 2023/3/2 12:46
 */
@Data
public class TeachPageRequest extends BaseRequest {

    private String teacherId;

    private String courseCode;

}
