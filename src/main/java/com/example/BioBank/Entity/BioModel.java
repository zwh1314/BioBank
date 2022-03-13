package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "bio_model")
public class BioModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    @ApiModelProperty(value = "生物样品id")
    private long modelId;

    @Column(name = "bank_id")
    @ApiModelProperty(value = "生物样品所属样本库id")
    private long bankId;

    @Column(name = "model_accessible")
    @ApiModelProperty(value = "生物样品的访问开关（1：样品开放；-1：样品隐藏）")
    private int modelAccessible;

    @Column(name = "model_tag",nullable = false)
    @ApiModelProperty(value = "生物样品标签")
    private String modelTag;

    @Column(name = "model_name",nullable = false)
    @ApiModelProperty(value = "生物样品名称")
    private String modelName;

    @Column(name = "model_description")
    @ApiModelProperty(value = "生物样品描述")
    private String modelDescription;

    @Column(name = "model_position",nullable = false)
    @ApiModelProperty(value = "生物样品存储位置")
    private String modelPosition;

    @Column(name = "model_in_time",nullable = false)
    @ApiModelProperty(value = "生物样品入库时间")
    private Date modelInTime;

    @Column(name = "model_modify_time",nullable = false)
    @ApiModelProperty(value = "生物样品最后一次被改动时间")
    private Date modelModifyTime;

    @Column(name = "model_temperature_most")
    @ApiModelProperty(value = "生物样品存储最高温度条件")
    private float modelTemperatureMost;

    @Column(name = "model_temperature_least")
    @ApiModelProperty(value = "生物样品存储最低温度条件")
    private float modelTemperatureLeast;
}
