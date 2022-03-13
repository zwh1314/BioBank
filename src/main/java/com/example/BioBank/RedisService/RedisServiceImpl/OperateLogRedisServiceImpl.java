package com.example.BioBank.RedisService.RedisServiceImpl;

import com.example.BioBank.Dao.OperateLogDao;
import com.example.BioBank.RedisService.OperateLogRedisService;
import com.example.BioBank.Response.Response;
import com.example.BioBank.utils.RedisUtil;
import com.example.BioBank.utils.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperateLogRedisServiceImpl implements OperateLogRedisService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OperateLogDao operateLogDao;

    private static final Logger logger = LoggerFactory.getLogger(OperateLogRedisServiceImpl.class);

    @Override
    public String commentLikeKey(long commentId) {
       return "commentLike:" + commentId;
    }

    @Override
    public Long getCommentLikeFromRedis(long commentId) {
        long commentLike;
        try{
            Object o = redisUtil.get(commentLikeKey(commentId));
            if (o == null)
                return null;
            else commentLike = Long.parseLong(String.valueOf(o));
        }catch (Exception e){
            logger.error("[getCommentLikeFromRedis Fail], commentId:{}",SerialUtil.toJsonStr(commentId));
            e.printStackTrace();
            return null;
        }
        return commentLike;
    }

    @Override
    public Response<Boolean> likesComment(long commentId) {
        Response<Boolean> response = new Response<>();
        boolean result;
        Long like = getCommentLikeFromRedis(commentId);
        if(like != null){
            result = redisUtil.set(commentLikeKey(commentId),like+1);
        }else{
            result =  redisUtil.set(commentLikeKey(commentId),like+1);
        }
        if(result) {
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Long> getCommentLikeByCommentId(long commentId) {
        Response<Long> response = new Response<>();
        Long like = getCommentLikeFromRedis(commentId);
        if(like == null){
            redisUtil.set(commentLikeKey(commentId),like);
        }
        response.setSuc(like);
        return response;
    }

}
