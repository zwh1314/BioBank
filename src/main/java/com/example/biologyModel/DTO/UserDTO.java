package com.example.biologyModel.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户登录注册模型")
@Data
public class UserDTO {

    @ApiModelProperty(value = "用户id")
    private long userId;

    @ApiModelProperty(value = "优先级")
    private String priority;

    @ApiModelProperty(value = "手机号")
    private String tel;

}
