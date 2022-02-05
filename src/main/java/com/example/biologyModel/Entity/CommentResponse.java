package com.example.biologyModel.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "comment_response",
        indexes = {@Index(columnList = "response_comment")})
public class CommentResponse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    @ApiModelProperty(value = "评论回复id")
    private long responseId;

    @Column(name = "response_comment")
    @ApiModelProperty(value = "回复对应评论id")
    private long commentId;

    @Column(name = "response_type")
    @ApiModelProperty(value = "回复对应评论的种类")
    private long responseType;

    @Column(name = "response_text")
    @ApiModelProperty(value = "评论回复文本")
    private String responseText;

    @Column(name = "response_publisher")
    @ApiModelProperty(value = "评论回复发布者")
    private long responsePublisher;

    @Column(name = "response_like")
    @ApiModelProperty(value = "评论回复赞数")
    private long responseLike;

    @Column(name = "response_date")
    @ApiModelProperty(value = "评论回复发布日期")
    private Date responseDate;
}
