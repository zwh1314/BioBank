package com.example.biologyModel.RedisService.RedisServiceImpl;

import com.example.biologyModel.Dao.UserInfoDao;
import com.example.biologyModel.RedisService.CreditsRedisService;
import com.example.biologyModel.Response.Response;
import com.example.biologyModel.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class CreditsRedisServiceImpl implements CreditsRedisService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserInfoDao userinfoDao;

    @Override
    public Response<Boolean> updateCredits(String userId, int change) {
        Response<Boolean> response=new Response<>();
        Integer credits = getCreditsFromRedis(userId);

        boolean result;

        if (credits != null) {
            result = redisUtil.hset("credit",userId, credits + change);
        } else {

            credits = Optional.ofNullable(userinfoDao.getCreditsById(Long.parseLong(userId)).getCredits()).orElse(0);

            result = redisUtil.hset("credit",userId, credits + change);
        }

        if(result)response.setSuc(true);

        return response;
    }
   @Override
    public Integer getCreditsFromRedis(String userId){
        Integer credits;
        try{
            credits = (Integer)redisUtil.hget("credit",userId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return credits;
    }

    @Override
    public int getCredits(String userId) {
        Integer credits = getCreditsFromRedis(userId);
        if(credits != null)return credits;

        credits = Optional.ofNullable(userinfoDao.getCreditsById(Long.parseLong(userId)).getCredits()).orElse(0);

        redisUtil.hset("credit",userId,credits);
        return credits;

    }


}
