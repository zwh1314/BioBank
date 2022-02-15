package com.example.BioBank.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "acitivty_signfile_model",
        indexes = {@Index(columnList = "activity_id")})
public class ActivitySignFileModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_model_id")
    @ApiModelProperty(value = "报名文件模板id")
    private long fileModelId;

    @Column(name = "activity_id")
    @ApiModelProperty(value = "活动id")
    private long activityId;

    @Column(name = "file_model_name")
    @ApiModelProperty(value = "活动报名文件名字")
    private String fileModelName;

    @Column(name = "file_model_url")
    @ApiModelProperty(value = "活动报名文件url")
    private String fileModelUrl;
}
