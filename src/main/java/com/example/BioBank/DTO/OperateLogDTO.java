package com.example.BioBank.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("生物样品操作日志模型")
@Data
public class OperateLogDTO {
    @ApiModelProperty(value = "生物样品（库）操作日志id")
    private long operateLogId;

    @ApiModelProperty(value = "操作用户id")
    private long userId;

    @ApiModelProperty(value = "操作的样品库id")
    private long bankId;

    @ApiModelProperty(value = "操作的样品id")
    private long modelId;

    @ApiModelProperty(value = "操作时间")
    private Date operateTime;

    @ApiModelProperty(value = "操作类型（1: 新增；2: 移除；3: 改动；4: 查找）")
    private int operateType;
}
