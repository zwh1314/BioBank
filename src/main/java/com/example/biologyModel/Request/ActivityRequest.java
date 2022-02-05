package com.example.biologyModel.Request;

import com.example.biologyModel.Entity.Activity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("保存活动信息")
@Data
@ToString
public class ActivityRequest {
    @ApiModelProperty(value = "活动列表", required = true)
    private List<Activity> activityList;
}