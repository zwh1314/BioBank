package com.example.BioBank.Request;

import com.example.BioBank.Entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("保存用户信息")
@Data
@ToString
public class UserInfoRequest {
    @ApiModelProperty(value = "用户信息列表", required = true)
    private List<UserInfo> userInfoList;
}
