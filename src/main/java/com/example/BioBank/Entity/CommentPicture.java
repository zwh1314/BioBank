package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "comment_picture",
        indexes = {@Index(columnList = "comment_id")})
public class CommentPicture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    @ApiModelProperty(value = "评论图片id")
    private long pictureId;

    @Column(name = "comment_id")
    @ApiModelProperty(value = "图片所属评论id")
    private long commentId;

    @Column(name = "file_name")
    @ApiModelProperty(value = "图片所属评论名字")
    private String fileName;

    @Column(name = "picture_url")
    @ApiModelProperty(value = "评论图片url")
    private String pictureUrl;
}
