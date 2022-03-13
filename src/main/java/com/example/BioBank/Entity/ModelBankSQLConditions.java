package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("生物样品库查询条件模型")
@Data
public class ModelBankSQLConditions {
    @ApiModelProperty(value = "样本库id")
    private long bankId;

    @ApiModelProperty("样本库容量")
    private int bankCapacity;

    @ApiModelProperty("样本库名称")
    private String bankName;

    @ApiModelProperty("样品库的访问开关（1：样品开放；-1：样品隐藏）")
    private int bankAccessible;
}
