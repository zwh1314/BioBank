package com.example.volunteer.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "goods")
public class Goods implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    @ApiModelProperty(value = "商品id")
    private long goodsId;

    @Column(name = "value",nullable = false)
    @ApiModelProperty(value = "价格")
    private long value;

    @Column(name = "num",nullable = false)
    @ApiModelProperty(value = "库存")
    private long num;

    @Column(name = "intro",nullable = false)
    @ApiModelProperty(value = "介绍")
    private String intro;

    @Column(name = "name")
    @ApiModelProperty(value = "名字")
    private String name;

    @Column(name = "goods_picture_url")
    @ApiModelProperty(value = "商品封面图片")
    private String goodsPictureUrl;

    @Column(name = "goods_picture_intro_url")
    @ApiModelProperty(value = "商品介绍图片")
    private String goodsPictureIntroUrl;

}
