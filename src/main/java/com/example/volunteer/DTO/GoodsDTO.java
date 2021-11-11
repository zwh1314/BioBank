package com.example.volunteer.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("Goods模型")
@Data
public class GoodsDTO implements Serializable {
    @ApiModelProperty(value = "商品id")
    private long goodsId;

    @ApiModelProperty(value = "价格")
    private long value;

    @ApiModelProperty(value = "库存")
    private long num;

    @ApiModelProperty(value = "介绍")
    private String intro;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "商品封面图片")
    private String goodsPictureUrl;


    @ApiModelProperty(value = "商品介绍图片")
    private String goodsPictureIntroUrl;
}
