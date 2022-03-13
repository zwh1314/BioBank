package com.example.BioBank.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("生物样品信息模型")
@Data
public class BioModelInfoDTO implements Serializable {
    @ApiModelProperty(value = "生物样品id")
    private long modelId;

    @ApiModelProperty(value = "生物样品所属样本库id")
    private long bankId;

    @ApiModelProperty(value = "生物样品的访问开关（1：样品开放；-1：样品隐藏）")
    private int modelAccessible;

    @ApiModelProperty(value = "生物样品标签")
    private String modelTag;

    @ApiModelProperty(value = "生物样品名称")
    private String modelName;

    @ApiModelProperty(value = "生物样品描述")
    private String modelDescription;

    @ApiModelProperty(value = "生物样品存储位置")
    private String modelPosition;

    @ApiModelProperty(value = "生物样品入库时间")
    private Date modelInTime;

    @ApiModelProperty(value = "生物样品最后一次被改动时间")
    private Date modelModifyTime;

    @ApiModelProperty(value = "生物样品存储最高温度条件")
    private float modelTemperatureMost;

    @ApiModelProperty(value = "生物样品存储最低温度条件")
    private float modelTemperatureLeast;
}
