package com.example.BioBank.RedisService;

import com.example.BioBank.Response.Response;

public interface OperateLogRedisService {

/*    public void commentLikeSchedule();*/
    String commentLikeKey(long commentId);

    Long getCommentLikeFromRedis(long commentId);

    Response<Boolean> likesComment(long commentId);

    Response<Long> getCommentLikeByCommentId(long commentId);

}
