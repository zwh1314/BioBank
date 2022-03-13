package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_bank_relation")
public class UserBankRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ub_relation_id")
    @ApiModelProperty(value = "用户-样本库权限关系id")
    private long ubRelationId;

    @Column(name = "user_id",nullable = false)
    @ApiModelProperty(value = "用户id")
    private long userId;

    @Column(name = "bank_id",nullable = false)
    @ApiModelProperty(value = "样本库id")
    private long bankId;

    @Column(name = "ud_priority",nullable = false)
    @ApiModelProperty(value = "用户-样本库权限关系(000 三位分别对应相应用户对相应样品库的删改查权限)")
    private int ubPriority;
}
