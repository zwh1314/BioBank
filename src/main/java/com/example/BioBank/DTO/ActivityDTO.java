package com.example.BioBank.DTO;

import com.example.BioBank.Entity.ActivityPicture;
import com.example.BioBank.Entity.ActivitySignFileModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    private long activityOrganizer;

    @ApiModelProperty("活动已报名人数")
    private int enrolledNumber;

    @ApiModelProperty("活动需要人数")
    private int requestedNumber;

    @ApiModelProperty("活动类型")
    private String activityType;

    @ApiModelProperty(value = "活动时间")
    private Date activityDate;

    @ApiModelProperty(value = "活动地点")
    private String activityPlace;

    @ApiModelProperty(value = "活动图片")
    private List<ActivityPicture> activityPictureList;

    @ApiModelProperty(value = "活动报名表模板")
    private List<ActivitySignFileModel> activitySignFileModelList;
}