package com.example.biologyModel.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "activity_signfile",
        indexes = {@Index(columnList = "activity_id"),@Index(columnList = "user_id")})
public class ActivitySignFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    @ApiModelProperty(value = "记录id")
    private long id;

    @Column(name = "activity_id",nullable = false)
    @ApiModelProperty(value = "活动id")
    private long activityId;

    @Column(name = "user_id",nullable = false)
    @ApiModelProperty(value = "报名用户id")
    private long userId;

    @Column(name = "file_name")
    @ApiModelProperty(value = "活动报名文件名字")
    private String fileName;

    @Column(name = "file_url")
    @ApiModelProperty(value = "活动报名文件url")
    private String fileUrl;
}
