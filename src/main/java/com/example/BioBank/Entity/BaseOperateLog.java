package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "base_operate_log")
public class BaseOperateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "base_operate_log_id")
    @ApiModelProperty(value = "基本操作记录id")
    private long baseOperateLogId;

    @Column(name = "operate_time",nullable = false)
    @ApiModelProperty(value = "基本操作时间")
    private Date operateTime;

    @Column(name = "operate_description",nullable = false)
    @ApiModelProperty(value = "操作详细描述")
    private String operateDescription;
}
