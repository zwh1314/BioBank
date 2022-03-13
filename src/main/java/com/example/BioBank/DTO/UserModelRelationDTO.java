package com.example.BioBank.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户-样品关系模型")
@Data
public class UserModelRelationDTO {
    @ApiModelProperty(value = "用户id")
    private long userId;

    @ApiModelProperty(value = "样本id")
    private long modelId;

    @ApiModelProperty(value = "用户-样本权限关系(000 三位分别对应相应用户对相应样品的删改查权限)")
    private int umPriority;
}
