package com.example.volunteer.utils;

import com.example.volunteer.Dao.*;
import com.example.volunteer.Entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledTasks {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    CommentResponseDao commentResponseDao;

    @Autowired
    VideoCommentDao videoCommentDao;

    @Autowired
    VideoDao videoDao;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    //每隔1 day执行一次
    @Scheduled(fixedRate = 60000*60*24)
    public void reportCurrentTime() {
        try{

            Map map;
            map = redisUtil.hmget("credit");

            Iterator<Map.Entry<String, Integer>> entries = map.entrySet().iterator();

            while (entries.hasNext()) {

                Map.Entry<String, Integer> entry = entries.next();
                UserInfo userInfo = new UserInfo();
                userInfo.setCredits(entry.getValue());
                userInfo.setUserId(Long.parseLong(entry.getKey()));
                userInfoDao.updateUserInfo(userInfo);

            }


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void commentLikeSchedule(){
        List<Long> commentIds = commentDao.getCommentIds();
        long like;
        for (Long commentId : commentIds) {
            try {
                Object o = redisUtil.get("commentLike:" + commentId);
                if (o != null) {
                    like = Long.parseLong(String.valueOf(o));
                    commentDao.updateCommentLike(like, commentId);
                }
            } catch (Exception e) {
                logger.warn("[Redis commentLike is not maintained], commentId:{}", SerialUtil.toJsonStr(commentId),e);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void responseLikeSchedule(){
        List<Long> responseIds = commentResponseDao.getResponseIds();
        long like;
        for (Long responseId : responseIds) {
            try {
                Object o = redisUtil.get("responseLike:" +responseId);
                if (o != null) {
                    like = Long.parseLong(String.valueOf(o));
                    commentResponseDao.updateResponseLike(like, responseId);
                }
            } catch (Exception e) {
                logger.warn("[Redis is not maintained], responseId:{}", SerialUtil.toJsonStr(responseId),e);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void VideoCommentLikeSchedule(){
        List<Long> commentIds = videoCommentDao.getCommentIds();
        long like;
        for (Long commentId : commentIds) {
            try {
                Object o = redisUtil.get("videoCommentLike:"+commentId);
                if (o != null) {
                    like = Long.parseLong(String.valueOf(o));
                    videoCommentDao.updateCommentLike(like, commentId);
                }
            } catch (Exception e) {
                logger.warn("[Redis is not maintained], commentId:{}", SerialUtil.toJsonStr(commentId),e);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void videoLikeSchedule(){
        List<Long> videoIds = videoDao.getVideoIds();
        long like;
        for (Long videoId : videoIds) {
            try {
                Object o = redisUtil.get("videoLike:"+ videoId);
                if (o != null) {
                    like = Long.parseLong(String.valueOf(o));
                    videoDao.updateVideoLike(like,videoId);
                }
            } catch (Exception e) {
                logger.warn("[Redis is not maintained], videoId:{}", SerialUtil.toJsonStr(videoId),e);
            }
        }
    }
}
