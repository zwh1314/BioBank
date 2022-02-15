package com.example.BioBank.RedisService;

import com.example.BioBank.Response.Response;

public interface VideoCommentRedisService {

    String videoCommentLikeKey(long commentId);

    Long getCommentLikeFromRedis(long commentId);

    Response<Boolean> likesVideoComment(long commentId);

    Response<Long> getCommentLikeByCommentId(long commentId);
}
