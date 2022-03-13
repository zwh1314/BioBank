package com.example.BioBank.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("基本操作日志模型")
@Data
public class BaseOperateLogDTO {
    @ApiModelProperty(value = "基本操作记录id")
    private long baseOperateLogId;

    @ApiModelProperty(value = "基本操作时间")
    private Date operateTime;

    @ApiModelProperty(value = "操作详细描述")
    private String operateDescription;
}
