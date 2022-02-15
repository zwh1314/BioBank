package com.example.BioBank.Request;

import com.example.BioBank.Entity.Video;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("保存视频信息")
@Data
@ToString
public class VideoRequest {
    @ApiModelProperty(value = "视频列表", required = true)
    private List<Video> videoList;
}
