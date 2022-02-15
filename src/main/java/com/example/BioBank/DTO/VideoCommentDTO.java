package com.example.BioBank.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("视频评论模型")
@Data
public class VideoCommentDTO {
    @ApiModelProperty(value = "视频评论id")
    private long commentId;

    @ApiModelProperty(value = "视频id")
    private long videoId;

    @ApiModelProperty(value = "视频评论文本")
    private String commentText;

    @ApiModelProperty(value = "视频评论发布者")
    private UserInfoDTO commentPublisher;

    @ApiModelProperty(value = "视频评论赞数")
    private long commentLike;

    @ApiModelProperty(value = "视频评论发布日期")
    private Date commentDate;

    @ApiModelProperty(value = "视频评论回复")
    private List<CommentResponseDTO> commentResponseList;
}
