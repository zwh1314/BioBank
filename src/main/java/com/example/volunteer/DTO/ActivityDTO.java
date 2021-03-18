package com.example.volunteer.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
/**
 * @param: none
 * @description: 返回给前端的活动信息实体类
 *
 **/
@ApiModel("活动模型")
@Data
public class ActivityDTO{

    @ApiModelProperty(value = "活动id")
    private long activityId;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动内容")
    private String activityContent;

    @ApiModelProperty(value = "活动组织者")
    private String activityOrganizer;

    @ApiModelProperty("活动已报名人数")
    private int enrolledNumber;

    @ApiModelProperty("活动需要人数")
    private int requestedNumber;

    @ApiModelProperty("活动类型")
    private String activityType;

    @ApiModelProperty(value = "活动时间")
    private Date activityDate;
}