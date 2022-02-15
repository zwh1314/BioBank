package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "news_picture",
        indexes = {@Index(columnList = "news_id")})
public class NewsPicture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    @ApiModelProperty(value = "活动图片id")
    private long pictureId;

    @Column(name = "news_id")
    @ApiModelProperty(value = "图片所属新闻id")
    private long newsId;

    @Column(name = "picture_url")
    @ApiModelProperty(value = "活动新闻图片url")
    private String pictureUrl;
}
