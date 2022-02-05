package com.example.biologyModel.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("评论回复模型")
@Data
public class CommentResponseDTO {
    @ApiModelProperty(value = "评论回复id")
    private long responseId;

    @ApiModelProperty(value = "回复对应评论id")
    private long commentId;

    @ApiModelProperty(value = "回复对应评论的种类")
    private long responseType;

    @ApiModelProperty(value = "评论回复文本")
    private String responseText;

    @ApiModelProperty(value = "评论回复发布者")
    private UserInfoDTO responsePublisher;

    @ApiModelProperty(value = "评论回复赞数")
    private long responseLike;

    @ApiModelProperty(value = "评论回复发布日期")
    private Date responseDate;
}
