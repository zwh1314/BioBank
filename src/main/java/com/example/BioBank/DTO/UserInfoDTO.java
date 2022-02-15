package com.example.BioBank.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("用户信息模型")
@Data
public class UserInfoDTO {

    @ApiModelProperty(value = "用户id")
    private long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "优先级")
    private String priority;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "头像")
    private String headPicture;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "手机")
    private String tel;

    @ApiModelProperty(value = "QQ")
    private String qq;

    @ApiModelProperty(value = "邮箱地址")
    private String mailAddress;

    @ApiModelProperty(value = "个性签名")
    private String introduction;

    @ApiModelProperty(value = "积分")
    private Integer credits;
}
