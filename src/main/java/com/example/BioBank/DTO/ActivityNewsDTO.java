package com.example.BioBank.DTO;

import com.example.BioBank.Entity.NewsPicture;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("活动新闻模型")
@Data
public class ActivityNewsDTO {
    @ApiModelProperty(value = "活动新闻id")
    private long newsId;

    @ApiModelProperty(value = "活动新闻对应活动id")
    private long activityId;

    @ApiModelProperty(value = "活动新闻标题")
    private String newsTitle;

    @ApiModelProperty(value = "活动新闻内容")
    private String newsContent;

    @ApiModelProperty(value = "活动新闻发布者")
    private long newsPublisher;

    @ApiModelProperty(value = "活动新闻发布时间")
    private Date newsDate;

    @ApiModelProperty(value = "活动新闻图片")
    private List<NewsPicture> newsPictureList;
}
