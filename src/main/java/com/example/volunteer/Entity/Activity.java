package com.example.volunteer.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity(name = "activity")
public class Activity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    @ApiModelProperty(value = "活动id")
    private long activityId;

    @Column(name = "activity_name",nullable = false)
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @Column(name = "activity_content",nullable = false)
    @ApiModelProperty(value = "活动内容")
    private String activityContent;

    @Column(name = "activity_organizer",nullable = false)
    @ApiModelProperty(value = "活动组织者")
    private long activityOrganizer;

    @Column(name = "enrolled_number" ,nullable = false)
    @ApiModelProperty("活动已报名人数")
    private int enrolledNumber;

    @Column(name = "requested_number",nullable = false)
    @ApiModelProperty("活动需要人数")
    private int requestedNumber;

    @Column(name = "activity_type",nullable = false)
    @ApiModelProperty("活动类型")
    private String activityType;

    @Column(name = "activity_date",nullable = false)
    @ApiModelProperty(value = "活动时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date activityDate;

    @Column(name = "activity_place",nullable = false)
    @ApiModelProperty(value = "活动地点")
    private String activityPlace;

    @Column(name = "is_signfile_model",nullable = false)
    @ApiModelProperty(value = "是否已上传报名表")
    private boolean isSignFileModel;

    @Column(name = "is_activity_picture",nullable = false)
    @ApiModelProperty(value = "是否已上传活动图片")
    private boolean isActivityPicture;


}
