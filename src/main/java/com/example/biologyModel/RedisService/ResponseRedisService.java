package com.example.biologyModel.RedisService;

import com.example.biologyModel.Response.Response;

public interface ResponseRedisService {
    String responseLikeKey(long responseId);

    Long getResponseLikeFromRedis(long responseId);

    Response<Boolean> likesResponse(long responseId);

    Response<Long> getResponseLikeByResponseId(long responseId);
}
