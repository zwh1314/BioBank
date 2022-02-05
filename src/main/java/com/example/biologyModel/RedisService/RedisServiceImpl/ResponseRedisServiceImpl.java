package com.example.biologyModel.RedisService.RedisServiceImpl;

import com.example.biologyModel.Dao.CommentResponseDao;
import com.example.biologyModel.RedisService.ResponseRedisService;
import com.example.biologyModel.Response.Response;
import com.example.biologyModel.utils.RedisUtil;
import com.example.biologyModel.utils.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResponseRedisServiceImpl implements ResponseRedisService {

    @Autowired
   private RedisUtil redisUtil;

    @Autowired
    private CommentResponseDao commentResponseDao;

    private static final Logger logger = LoggerFactory.getLogger(ResponseRedisServiceImpl.class);


    @Override
    public String responseLikeKey(long responseId) {
        return "responseLike:" +responseId ;
    }

    @Override
    public Long getResponseLikeFromRedis(long responseId) {
        long responseLike;
        try{
            Object o = redisUtil.get(responseLikeKey(responseId));
            if (o == null)
                return null;
            else responseLike = Long.parseLong(String.valueOf(o));
        }catch (Exception e){
            logger.error("[getResponseLikeFromRedis Fail], responseId：{}", SerialUtil.toJsonStr(responseId));
            e.printStackTrace();
            return  null;
        }
        return responseLike;
    }

    @Override
    public Response<Boolean> likesResponse(long responseId) {
        Response<Boolean> response = new Response<>();
        boolean result;
        Long like = getResponseLikeFromRedis(responseId);
        if (like != null){
            result = redisUtil.set(responseLikeKey(responseId),like+1);
        }else{
            like = Optional.ofNullable(commentResponseDao.getResponseLikeByResponseId(responseId)).orElse(0L);
            result =  redisUtil.set(responseLikeKey(responseId),like+1);
        }
        if(result)
            response.setSuc(true);
        return response;
    }

    @Override
    public Response<Long> getResponseLikeByResponseId(long responseId) {
        Response<Long> response = new Response<>();
        Long like = getResponseLikeFromRedis(responseId);
        if(like == null){
            like  = Optional.ofNullable(commentResponseDao.getResponseLikeByResponseId(responseId)).orElse(0L);
            redisUtil.set(responseLikeKey(responseId),like);
        }
        response.setSuc(like);
        return response;
    }
}
