package com.example.BioBank.Request;

import com.example.BioBank.Entity.VideoComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("保存视频评论信息")
@Data
@ToString
public class VideoCommentRequest {
    @ApiModelProperty(value = "视频评论列表", required = true)
    private List<VideoComment> videoCommentList;
}
