package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "user_info")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")
    private long userId;

    @Column(name = "user_name",nullable = false)
    @ApiModelProperty(value = "用户名")
    private String userName;

    @Column(name = "priority",nullable = false)
    @ApiModelProperty(value = "优先级")
    private String priority;

    @Column(name = "mail_address")
    @ApiModelProperty(value = "邮箱地址")
    private String mailAddress;

    @Column(name = "head_picture")
    @ApiModelProperty(value = "头像")
    private String headPicture;

    @Column(name = "introduction")
    @ApiModelProperty(value = "个性签名")
    private String introduction;

    @Column(name = "address")
    @ApiModelProperty(value = "地址")
    private String address;

    @Column(name = "gender")
    @ApiModelProperty(value = "性别")
    private String gender;

    @Column(name = "birthday")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    @Column(name = "major")
    @ApiModelProperty(value = "专业")
    private String major;

    @Column(name = "tel",nullable = false)
    @ApiModelProperty(value = "手机")
    private String tel;

    @Column(name = "qq")
    @ApiModelProperty(value = "QQ")
    private String qq;

    @Column(name = "credits")
    @ApiModelProperty(value = "credits")
    private int credits;
}
