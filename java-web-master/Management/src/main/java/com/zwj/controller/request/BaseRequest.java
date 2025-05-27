package com.zwj.controller.request;

import lombok.Data;


@Data
class BaseRequest {

    private Integer pageNum = 1;
    private Integer pageSize = 10;

}
