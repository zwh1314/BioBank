package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "user",
        indexes = {@Index(columnList = "tel")})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")
    private long userId;

    @Column(name = "password",nullable = false)
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "priority",nullable = false)
    @ApiModelProperty(value = "优先级")
    private String priority;

    @Column(name = "tel")
    @ApiModelProperty(value = "手机")
    private String tel;
}
