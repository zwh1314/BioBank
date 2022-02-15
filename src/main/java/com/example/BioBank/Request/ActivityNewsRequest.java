package com.example.BioBank.Request;

import com.example.BioBank.Entity.ActivityNews;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("保存活动新闻信息")
@Data
@ToString
public class ActivityNewsRequest {
    @ApiModelProperty(value = "活动新闻列表", required = true)
    private List<ActivityNews> activityNewsList;
}
