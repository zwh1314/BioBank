package com.example.biologyModel.Request;


import com.example.biologyModel.Entity.ActivityUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("保存活动用户信息")
@Data
@ToString
public class ActivityUserRequest {
    @ApiModelProperty(value = "活动用户列表", required = true)
    private List<ActivityUser> activityUserList;
}
