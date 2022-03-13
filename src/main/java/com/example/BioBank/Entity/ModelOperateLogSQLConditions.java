package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("生物样品操作日志查询条件模型")
@Data
public class ModelOperateLogSQLConditions {
    @ApiModelProperty(value = "操作用户id")
    private long userId;

    @ApiModelProperty(value = "操作的样品库id")
    private long bankId;

    @ApiModelProperty(value = "操作的样品id")
    private long modelId;


    @ApiModelProperty(value = "操作类型（1: 新增；2: 移除；3: 改动；4: 查找）")
    private int operateType;
}
