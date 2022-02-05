package com.example.biologyModel.Request;

import com.example.biologyModel.Entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("保存普通评论信息")
@Data
@ToString
public class CommentRequest {
    @ApiModelProperty(value = "普通评论列表", required = true)
    private List<Comment> commentList;
}
