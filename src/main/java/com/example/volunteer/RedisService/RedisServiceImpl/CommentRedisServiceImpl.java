package com.example.volunteer.RedisService.RedisServiceImpl;

import com.example.volunteer.Dao.CommentDao;
import com.example.volunteer.RedisService.CommentRedisService;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.ServiceImpl.CommentServiceImpl;
import com.example.volunteer.utils.RedisUtil;
import com.example.volunteer.utils.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CommentRedisServiceImpl implements CommentRedisService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommentDao commentDao;

    private static final Logger logger = LoggerFactory.getLogger(CommentRedisServiceImpl.class);

    @Override
    public String commentLikeKey(long commentId) {
       return "commentLike:" + commentId;
    }

    @Override
    public Long getCommentLikeFromRedis(long commentId) {
        Long commentLike;
        try{
            Object o = redisUtil.get(commentLikeKey(commentId));
            if (o == null)
                return null;
            else commentLike = Long.valueOf(String.valueOf(o));
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
            like = Optional.ofNullable(commentDao.getCommentLikeByCommentId(commentId)).orElse(0L);
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
            like  = Optional.ofNullable(commentDao.getCommentLikeByCommentId(commentId)).orElse(0L);
            redisUtil.set(commentLikeKey(commentId),like);
        }
        response.setSuc(like);
        return response;
    }

}
