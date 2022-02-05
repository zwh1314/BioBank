package com.example.biologyModel.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "video")
public class Video implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    @ApiModelProperty(value = "视频id")
    private long videoId;

    @Column(name = "video_url")
    @ApiModelProperty(value = "视频URL")
    private String videoUrl;

    @Column(name = "video_title")
    @ApiModelProperty(value = "视频标题")
    private String videoTitle;

    @Column(name = "video_face")
    @ApiModelProperty(value = "视频封面")
    private String videoFace;

    @Column(name = "video_text")
    @ApiModelProperty(value = "视频文本")
    private String videoText;

    @Column(name = "video_play_num")
    @ApiModelProperty(value = "视频播放量")
    private long videoPlayNum;

    @Column(name = "video_like")
    @ApiModelProperty(value = "视频赞数")
    private long videoLike;

    @Column(name = "video_publisher")
    @ApiModelProperty(value = "视频发布者")
    private long videoPublisher;

    @Column(name = "video_date")
    @ApiModelProperty(value = "视频发布时间")
    private Date videoDate;
}
