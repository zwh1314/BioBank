package com.example.BioBank.RedisService;

import com.example.BioBank.Response.Response;

public interface ResponseRedisService {
    String responseLikeKey(long responseId);

    Long getResponseLikeFromRedis(long responseId);

    Response<Boolean> likesResponse(long responseId);

    Response<Long> getResponseLikeByResponseId(long responseId);
}
