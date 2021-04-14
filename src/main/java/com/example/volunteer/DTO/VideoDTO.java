package com.example.volunteer.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("视频模型")
@Data
public class VideoDTO {
    @ApiModelProperty(value = "视频id")
    private long videoId;

    @ApiModelProperty(value = "视频URL")
    private String videoUrl;

    @ApiModelProperty(value = "视频标题")
    private String videoTitle;

    @ApiModelProperty(value = "视频封面")
    private String videoFace;

    @ApiModelProperty(value = "视频文本")
    private String videoText;

    @ApiModelProperty(value = "视频播放量")
    private long videoPlayNum;

    @ApiModelProperty(value = "视频赞数")
    private long videoLike;

    @ApiModelProperty(value = "视频发布者")
    private UserInfoDTO videoPublisher;

    @ApiModelProperty(value = "视频发布时间")
    private Date videoDate;

    @ApiModelProperty(value = "视频评论")
    private List<VideoCommentDTO> videoCommentDTOList;
}
