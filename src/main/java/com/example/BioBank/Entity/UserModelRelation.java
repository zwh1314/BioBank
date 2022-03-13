package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_model_relation")
public class UserModelRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "um_relation_id")
    @ApiModelProperty(value = "用户-样本权限关系id")
    private long umRelationId;

    @Column(name = "user_id",nullable = false)
    @ApiModelProperty(value = "用户id")
    private long userId;

    @Column(name = "model_id",nullable = false)
    @ApiModelProperty(value = "样本id")
    private long modelId;

    @Column(name = "um_priority",nullable = false)
    @ApiModelProperty(value = "用户-样本权限关系(000 三位分别对应相应用户对相应样品的删改查权限)")
    private int umPriority;
}
