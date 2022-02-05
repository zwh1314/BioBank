package com.example.biologyModel.Request;

import com.example.biologyModel.Entity.CommentResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("保存评论回复信息")
@Data
@ToString
public class CommentResponseRequest {
    @ApiModelProperty(value = "评论回复列表", required = true)
    private List<CommentResponse> commentResponseList;
}
