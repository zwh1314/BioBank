package com.example.biologyModel.RedisService;

import com.example.biologyModel.Response.Response;

public interface CreditsRedisService {

    /**
     * 更新积分
     */
    Response<Boolean> updateCredits(String userId, int change);

    /**
     * 查询积分
     */
    int getCredits(String userId);

    /**
     * 从redis里查询积分
     */
    Integer getCreditsFromRedis(String userId);



}

