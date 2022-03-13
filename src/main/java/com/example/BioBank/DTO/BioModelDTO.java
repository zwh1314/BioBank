package com.example.BioBank.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("生物样品模型")
@Data
public class BioModelDTO {
    @ApiModelProperty(value = "生物样品id")
    private long modelId;

    @ApiModelProperty(value = "生物样品所属样本库id")
    private long bankId;

    @ApiModelProperty(value = "生物样品的访问开关（1：样品开放；-1：样品隐藏）")
    private int modelAccessible;

    @ApiModelProperty(value = "生物样品名称")
    private String modelName;
}
