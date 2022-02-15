package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    @ApiModelProperty(value = "评论id")
    private long commentId;

    @Column(name = "comment_text")
    @ApiModelProperty(value = "评论文本")
    private String commentText;

    @Column(name = "comment_publisher")
    @ApiModelProperty(value = "评论发布者")
    private long commentPublisher;

    @Column(name = "comment_like")
    @ApiModelProperty(value = "评论赞数")
    private long commentLike;

    @Column(name = "comment_date")
    @ApiModelProperty(value = "评论发布日期")
    private Date commentDate;

}
