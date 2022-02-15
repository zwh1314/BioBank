package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table(name = "activity_picture",
        indexes = {@Index(columnList = "activity_id")})
public class ActivityPicture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    @ApiModelProperty(value = "活动图片id")
    private long pictureId;

    @Column(name = "activity_id")
    @ApiModelProperty(value = "图片所属活动id")
    private long activityId;

    @Column(name = "picture_url")
    @ApiModelProperty(value = "活动图片url")
    private String pictureUrl;
}
