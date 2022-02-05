package com.example.biologyModel.Service.ServiceImpl;

import com.example.biologyModel.Dao.CommentResponseDao;
import com.example.biologyModel.Entity.CommentResponse;
import com.example.biologyModel.Request.CommentResponseRequest;
import com.example.biologyModel.Response.Response;
import com.example.biologyModel.Service.CommentResponseService;
import com.example.biologyModel.enums.ResponseEnum;
import com.example.biologyModel.utils.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentResponseServiceImpl implements CommentResponseService {
    private static final Logger logger = LoggerFactory.getLogger(CommentResponseServiceImpl.class);

    @Autowired
    private CommentResponseDao commentResponseDao;

    @Override
    public Response<Boolean> addCommentResponse(CommentResponseRequest commentResponseRequest){
        Response<Boolean> response=new Response<>();

        for(CommentResponse commentResponse:commentResponseRequest.getCommentResponseList()) {
            boolean result = commentResponseDao.addCommentResponse(commentResponse) > 0;
            if (!result) {
                logger.error("[addCommentResponse Fail], request: {}", SerialUtil.toJsonStr(commentResponseRequest));
                response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
                return response;
            }
        }
        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Boolean> updateResponseLikeNumber(long responseLikeNumber, long responseId){
        Response<Boolean> response=new Response<>();
        boolean result = commentResponseDao.updateResponseLike(responseLikeNumber,responseId) > 0;
        if (!result) {
            logger.error("[updateResponseLike Fail], responseId: {}", SerialUtil.toJsonStr(responseId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Boolean> updateResponseText(String responseText, long responseId){
        Response<Boolean>  response=new Response<>();

        boolean result = commentResponseDao.updateResponseText(responseText,responseId) > 0;
        if (!result) {
            logger.error("[updateResponseText Fail], responseId: {}", SerialUtil.toJsonStr(responseId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else {
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<List<CommentResponse>> getCommentResponseByCommentId(long commentId){
        Response<List<CommentResponse>> response=new Response<>();

        List<CommentResponse> commentResponseList = commentResponseDao.findCommentResponseByCommentId(commentId, 0L);
        if (commentResponseList.size() == 0) {
            response.setFail(ResponseEnum.COMMENT_RESPONSE_COMMENT_NOT_FOUND);
        }
        else{
            response.setSuc(commentResponseList);
        }
        return response;
    }

    @Override
    public Response<List<CommentResponse>> getVideoCommentResponseByCommentId(long commentId){
        Response<List<CommentResponse>> response=new Response<>();

        List<CommentResponse> commentResponseList = commentResponseDao.findCommentResponseByCommentId(commentId, 1L);
        if (commentResponseList.size() == 0) {
            response.setFail(ResponseEnum.COMMENT_RESPONSE_VIDEO_COMMENT_NOT_FOUND);
        }
        else {
            response.setSuc(commentResponseList);
        }
        return response;
    }

    @Override
    public Response<List<CommentResponse>> getCommentResponseInOneWeek(){
        Response<List<CommentResponse>> response=new Response<>();

        List<CommentResponse> commentResponseList = commentResponseDao.findCommentResponseInOneWeek(1L);
        if (commentResponseList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        }
        else{
            response.setSuc(commentResponseList);
        }
        return response;
    }

    @Override
    public Response<List<CommentResponse>> getVideoCommentResponseInOneWeek(){
        Response<List<CommentResponse>> response=new Response<>();

        List<CommentResponse> commentResponseList = commentResponseDao.findCommentResponseInOneWeek(0L);
        if (commentResponseList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        }
        else{
            response.setSuc(commentResponseList);
        }
        return response;
    }

    @Override
    public Response<List<CommentResponse>> getCommentResponseByRelativeText(String relativeText){
        Response<List<CommentResponse>> response=new Response<>();

        List<CommentResponse> commentResponseList = commentResponseDao.findCommentResponseByText(relativeText, 0L);
        if (commentResponseList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_RELATIVE_TEXT_NOT_FOUND);
        }
        else{
            response.setSuc(commentResponseList);
        }
        return response;
    }

    @Override
    public Response<List<CommentResponse>> getVideoCommentResponseByRelativeText(String relativeText){
        Response<List<CommentResponse>> response=new Response<>();

        List<CommentResponse> commentResponseList = commentResponseDao.findCommentResponseByText(relativeText, 1L);
        if (commentResponseList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_RELATIVE_TEXT_NOT_FOUND);
        }
        else{
            response.setSuc(commentResponseList);
        }
        return response;
    }

    @Override
    public Response<List<CommentResponse>> getCommentResponseByPublisherId(long publisherId){
        Response<List<CommentResponse>> response=new Response<>();

        List<CommentResponse> commentResponseList = commentResponseDao.findCommentResponseByPublisher(publisherId, 0L);
        if (commentResponseList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_PUBLISHER_NOT_FOUND);
        }
        else{
            response.setSuc(commentResponseList);
        }
        return response;
    }

    @Override
    public Response<List<CommentResponse>> getVideoCommentResponseByPublisherId(long publisherId){
        Response<List<CommentResponse>> response=new Response<>();

        List<CommentResponse> commentResponseList = commentResponseDao.findCommentResponseByPublisher(publisherId,1L);
        if (commentResponseList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_PUBLISHER_NOT_FOUND);
        }
        else{
            response.setSuc(commentResponseList);
        }
        return response;
    }

    @Override
    public Response<Boolean> deleteCommentResponseById(long responseId){
        Response<Boolean> response=new Response<>();

        boolean result= commentResponseDao.deleteCommentResponseById(responseId) > 0;
        if(!result){
            logger.error("[deleteCommentResponseById Fail], responseId: {}", SerialUtil.toJsonStr(responseId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<List<CommentResponse>> getVideoCommentResponseByNumber(long number){
        Response<List<CommentResponse>> response=new Response<>();

        List<CommentResponse> commentResponseList = commentResponseDao.findCommentResponseByNumber(number,1);//假设1对应Video类型
        if (commentResponseList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        }
        else{
            response.setSuc(commentResponseList);
        }
        return response;
    }


}
