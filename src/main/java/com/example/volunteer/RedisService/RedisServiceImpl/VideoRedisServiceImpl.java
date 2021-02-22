package com.example.volunteer.RedisService.RedisServiceImpl;

import com.example.volunteer.Dao.VideoDao;
import com.example.volunteer.RedisService.VideoRedisService;
import com.example.volunteer.Response.Response;
import com.example.volunteer.utils.RedisUtil;
import com.example.volunteer.utils.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VideoRedisServiceImpl implements VideoRedisService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    VideoDao videoDao;

    private static final Logger logger = LoggerFactory.getLogger(VideoRedisServiceImpl.class);

    @Override
    public String videoLikeKey(long videoId) {
        return "videoLike:"+ videoId;
    }

    @Override
    public Long getVideoLikeFromRedis(long videoId) {
        Long like;
        try{
            Object o = redisUtil.get(videoLikeKey(videoId));
            if (o == null)
                return null;
            else like = Long.valueOf(String.valueOf(o));
        }catch (Exception e){
            logger.error("[getVideoLikeFromRedis Fail], videoId：{}", SerialUtil.toJsonStr(videoId));
            e.printStackTrace();
            return  null;
        }
        return like;
    }

    @Override
    public Response<Boolean> likesVideo(long videoId) {
        Response<Boolean> response = new Response<>();
        boolean result;
        Long like = getVideoLikeFromRedis(videoId);
        if (like != null){
            result = redisUtil.set(videoLikeKey(videoId),like+1);
        }else{
            like = Optional.ofNullable(videoDao.getVideoLikeByVideoId(videoId)).orElse(0L);
            result =  redisUtil.set(videoLikeKey(videoId),like+1);
        }
        if(result){
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Long> getVideoLikeByVideoId(long videoId) {
        Response<Long> response = new Response<>();
        Long like = getVideoLikeFromRedis(videoId);
        if(like == null){
            like  = Optional.ofNullable(videoDao.getVideoLikeByVideoId(videoId)).orElse(0L);
            redisUtil.set(videoLikeKey(videoId),like);
        }
        response.setSuc(like);
        return response;
    }
}
