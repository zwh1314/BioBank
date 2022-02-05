package com.example.biologyModel.DTO;

import com.example.biologyModel.Entity.CommentPicture;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("动态模型")
@Data
public class CommentDTO {
    @ApiModelProperty(value = "评论id")
    private long commentId;

    @ApiModelProperty(value = "评论文本")
    private String commentText;

    @ApiModelProperty(value = "评论发布者")
    private UserInfoDTO commentPublisher;

    @ApiModelProperty(value = "评论赞数")
    private long commentLike;

    @ApiModelProperty(value = "评论发布日期")
    private Date commentDate;

    @ApiModelProperty(value = "评论图片")
    private List<CommentPicture> commentPictureList;

    @ApiModelProperty(value = "评论回复")
    private List<CommentResponseDTO> commentResponseList;
}
