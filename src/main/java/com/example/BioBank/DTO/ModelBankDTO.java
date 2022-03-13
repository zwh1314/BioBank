package com.example.BioBank.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("样本库模型")
@Data
public class ModelBankDTO {

    @ApiModelProperty(value = "样本库id")
    private long bankId;

    @ApiModelProperty("样本库容量")
    private int bankCapacity;

    @ApiModelProperty("样本库名称")
    private String bankName;

    @ApiModelProperty("样品库的访问开关（1：样品开放；-1：样品隐藏）")
    private int bankAccessible;

    @ApiModelProperty(value = "样品库最后一次被改动的时间")
    private Date bankModifyTime;
}