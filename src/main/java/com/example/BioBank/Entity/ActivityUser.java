package com.example.BioBank.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "activity_user",
        indexes = {@Index(columnList = "activity_id"),@Index(columnList = "user_id")})
public class ActivityUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    @ApiModelProperty(value = "记录id")
    private long id;

    @Column(name = "activity_id",nullable = false)
    @ ApiModelProperty(value = "活动id")
    private long activityId;

    @Column(name = "user_id",nullable = false)
    @ApiModelProperty(value = "用户id")
    private long userId;

    @Column(name = "form_status")
    @ApiModelProperty(value = "报名表提交状态")
    private boolean formStatus;//1代表提交，0代表未交

    @Column(name = "is_focus")
    @ApiModelProperty(value = "是否关注")
    private boolean isFocus;

    @Column(name = "form_date")
    @ApiModelProperty(value = "报名表提交时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date formDate;

    public void setIsFocus(boolean focus) {
        this.isFocus = focus;
    }

}