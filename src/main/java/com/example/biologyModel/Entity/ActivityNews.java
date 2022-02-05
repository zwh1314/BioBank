package com.example.biologyModel.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "activity_news")
public class ActivityNews implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    @ApiModelProperty(value = "活动新闻id")
    private long newsId;

    @Column(name = "activity_id")
    @ApiModelProperty(value = "活动新闻对应活动id")
    private long activityId;

    @Column(name = "news_title")
    @ApiModelProperty(value = "活动新闻标题")
    private String newsTitle;

    @Column(name = "news_content")
    @ApiModelProperty(value = "活动新闻内容")
    private String newsContent;

    @Column(name = "news_publisher")
    @ApiModelProperty(value = "活动新闻发布者")
    private long newsPublisher;

    @Column(name = "news_date")
    @ApiModelProperty(value = "活动新闻发布时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date newsDate;

}
