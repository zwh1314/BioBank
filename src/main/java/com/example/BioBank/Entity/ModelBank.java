package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity(name = "model_bank")
public class ModelBank implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    @ApiModelProperty(value = "样本库id")
    private long bankId;

    @Column(name = "bank_capacity", nullable = false)
    @ApiModelProperty("样本库容量")
    private int bankCapacity;

    @Column(name = "bank_name", nullable = false)
    @ApiModelProperty("样本库名称")
    private String bankName;

    @Column(name = "bank_accessible", nullable = false)
    @ApiModelProperty("样品库的访问开关（1：样品开放；-1：样品隐藏）")
    private int bankAccessible;

    @Column(name = "bank_modify_time", nullable = false)
    @ApiModelProperty(value = "样品库最后一次被改动时间")
    private Date bankModifyTime;
}
