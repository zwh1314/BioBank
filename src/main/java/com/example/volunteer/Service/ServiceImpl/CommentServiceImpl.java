package com.example.volunteer.Service.ServiceImpl;

import com.example.volunteer.Dao.CommentDao;
import com.example.volunteer.Entity.Comment;
import com.example.volunteer.Exception.VolunteerRuntimeException;
import com.example.volunteer.Request.CommentRequest;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.CommentService;
import com.example.volunteer.enums.ResponseEnum;
import com.example.volunteer.utils.RedisUtil;
import com.example.volunteer.utils.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Autowired
    RedisUtil redisUtil;
    @Override
    public Response<Boolean> addComment(CommentRequest commentRequest){
        Response<Boolean> response=new Response<>();

        for(Comment comment:commentRequest.getCommentList()) {
            boolean result = commentDao.addComment(comment) > 0;
            if (!result) {
                logger.error("[addComment Fail], request: {}", SerialUtil.toJsonStr(commentRequest));
                response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
                return response;
            }
        }
        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Boolean> updateCommentLikeNumber(long commentLikeNumber, long commentId){
        Response<Boolean> response=new Response<>();
        boolean result = commentDao.updateCommentLike(commentLikeNumber, commentId) > 0;
        if (!result) {
            logger.error("[updateCommentLike Fail], commentId: {}", SerialUtil.toJsonStr(commentId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Boolean> updateCommentText(String commentText, long commentId){
        Response<Boolean> response=new Response<>();
        boolean result = commentDao.updateCommentText(commentText,commentId) > 0;
        if (!result) {
            logger.error("[updateCommentText Fail], commentId: {}", SerialUtil.toJsonStr(commentId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else
        {
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<List<Comment>> getCommentByPublisher(long publisherId){
        Response<List<Comment>> response=new Response<>();

        List<Comment> commentList = commentDao.findCommentById(publisherId);
        if (commentList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_PUBLISHER_NOT_FOUND);
        }
        else
        {
            response.setSuc(commentList);
        }
        return response;
    }

    @Override
    public Response<List<Comment>> getCommentInOneWeek(){
        Response<List<Comment>> response=new Response<>();

        List<Comment> commentList = commentDao.findCommentInOneWeek();
        if (commentList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        }
        else{
            response.setSuc(commentList);
        }
        return response;
    }

    @Override
    public Response<List<Comment>> getCommentByRelativeText(String relativeText){
        Response<List<Comment>> response=new Response<>();

        List<Comment> commentList = commentDao.findCommentByText(relativeText);
        if (commentList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_RELATIVE_TEXT_NOT_FOUND);
        }
        else
        {
            response.setSuc(commentList);
        }
        return response;
    }

    @Override
    public Response<Boolean> deleteCommentById(long commentId){
        Response<Boolean> response=new Response<>();

        boolean result=commentDao.deleteCommentById(commentId) > 0;
        if(!result){
            logger.error("[deleteCommentById Fail], commentId: {}", SerialUtil.toJsonStr(commentId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    /**
     * redis中存储key名称规则
     * @param commentId
     * @return redis:commentLike: commentId
     */
    public static String COMMENT_LIKE_KEY(long commentId){
        return "redis:commentLike:" + commentId;
    }

    /**
     * 从redis取出评论点赞数
     * @param commentId
     * @return redis连接失败返回null，成功返回赞数
     */
    private Long getCommentLikeFromRedis(long commentId){

        Long commentLike;
        try{
            Object o = redisUtil.get(COMMENT_LIKE_KEY(commentId));
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

    /**
     * 通过commentId查询评论点赞
     * @param commentId
     * @return 点赞数
     */
    @Override
    public long getCommentLikeByCommentId(long commentId) {
        Long like = getCommentLikeFromRedis(commentId);
        if (like != null) {
            return like;
        }
        like  = Optional.ofNullable(commentDao.getCommentLikeByCommentId(commentId)).orElse(0L);
        redisUtil.set(COMMENT_LIKE_KEY(commentId),like);
        return like;
    }

    /**
     * 点赞评论
     * @param commentId
     */
    @Override
    public boolean LikesComment(long commentId) {
        boolean result;
        Long like = getCommentLikeFromRedis(commentId);
        if(like != null){
            result = redisUtil.set(COMMENT_LIKE_KEY(commentId),like+1);
        }else{
            like = Optional.ofNullable(commentDao.getCommentLikeByCommentId(commentId)).orElse(0L);
            result =  redisUtil.set(COMMENT_LIKE_KEY(commentId),like+1);
        }
        return result;
    }

}
