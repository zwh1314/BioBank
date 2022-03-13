package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "model_operate_log")
public class OperateLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operate_log_id")
    @ApiModelProperty(value = "生物样品（库）操作日志id")
    private long operateLogId;

    @Column(name = "user_id",nullable = false)
    @ApiModelProperty(value = "操作用户id")
    private long userId;

    @Column(name = "bank_id",nullable = false)
    @ApiModelProperty(value = "操作的样品库id")
    private long bankId;

    @Column(name = "model_id")
    @ApiModelProperty(value = "操作的样品id")
    private long modelId;

    @Column(name = "operate_time",nullable = false)
    @ApiModelProperty(value = "操作时间")
    private Date operateTime;

    @Column(name = "operate_type",nullable = false)
    @ApiModelProperty(value = "操作类型（1: 新增；2: 移除；3: 改动；4: 查找）")
    private int operateType;
}
