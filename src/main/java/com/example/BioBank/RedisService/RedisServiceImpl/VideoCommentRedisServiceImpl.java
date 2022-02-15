package com.example.BioBank.RedisService.RedisServiceImpl;

import com.example.BioBank.Dao.VideoCommentDao;
import com.example.BioBank.RedisService.VideoCommentRedisService;
import com.example.BioBank.Response.Response;
import com.example.BioBank.utils.RedisUtil;
import com.example.BioBank.utils.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VideoCommentRedisServiceImpl implements VideoCommentRedisService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
   private VideoCommentDao videoCommentDao;

    private static final Logger logger = LoggerFactory.getLogger(VideoCommentRedisServiceImpl.class);


    @Override
    public  String videoCommentLikeKey(long commentId) {
        return "videoCommentLike:"+commentId;
    }

    @Override
    public Long getCommentLikeFromRedis(long commentId) {
        long commentLike;
        try{
            Object o = redisUtil.get(videoCommentLikeKey(commentId));
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
    public Response<Boolean> likesVideoComment(long commentId) {
        Response<Boolean> response = new Response<>();
        boolean result;
        Long like = getCommentLikeFromRedis(commentId);
        if(like != null){
            result = redisUtil.set(videoCommentLikeKey(commentId),like+1);
        }else{
            like = Optional.ofNullable(videoCommentDao.getCommentLikeByCommentId(commentId)).orElse(0L);
            result =  redisUtil.set(videoCommentLikeKey(commentId),like+1);
        }
        if(result)
            response.setSuc(true);
        return response;
    }

    @Override
    public Response<Long> getCommentLikeByCommentId(long commentId) {
        Response<Long> response = new Response<>();
        Long like = getCommentLikeFromRedis(commentId);
      if(like == null){
          like  = Optional.ofNullable(videoCommentDao.getCommentLikeByCommentId(commentId)).orElse(0L);
          redisUtil.set(videoCommentLikeKey(commentId),like);
      }
      response.setSuc(like);
        return response;
    }
}
