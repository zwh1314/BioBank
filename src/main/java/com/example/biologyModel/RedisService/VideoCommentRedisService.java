package com.example.biologyModel.RedisService;

import com.example.biologyModel.Response.Response;

public interface VideoCommentRedisService {

    String videoCommentLikeKey(long commentId);

    Long getCommentLikeFromRedis(long commentId);

    Response<Boolean> likesVideoComment(long commentId);

    Response<Long> getCommentLikeByCommentId(long commentId);
}
