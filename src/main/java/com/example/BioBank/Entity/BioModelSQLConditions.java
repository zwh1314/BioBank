package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("生物样品查询条件模型")
@Data
public class BioModelSQLConditions {
    @ApiModelProperty(value = "生物样品id")
    private long modelId;

    @ApiModelProperty(value = "生物样品所属样本库id")
    private long bankId;

    @ApiModelProperty(value = "生物样品标签")
    private String modelTag;

    @ApiModelProperty(value = "生物样品的访问开关（1：样品开放；-1：样品隐藏）")
    private int modelAccessible;

    @ApiModelProperty(value = "生物样品名称")
    private String modelName;

    @ApiModelProperty(value = "生物样品存储位置")
    private String modelPosition;
}
