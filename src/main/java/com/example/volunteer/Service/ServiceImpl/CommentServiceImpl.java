package com.example.volunteer.Service.ServiceImpl;

import com.example.volunteer.DTO.CommentDTO;
import com.example.volunteer.DTO.CommentResponseDTO;
import com.example.volunteer.Dao.CommentDao;
import com.example.volunteer.Dao.CommentPictureDao;
import com.example.volunteer.Dao.CommentResponseDao;
import com.example.volunteer.Dao.UserInfoDao;
import com.example.volunteer.Entity.Comment;
import com.example.volunteer.Entity.CommentPicture;
import com.example.volunteer.Entity.CommentResponse;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.CommentService;
import com.example.volunteer.enums.ResponseEnum;
import com.example.volunteer.utils.OSSUtil;
import com.example.volunteer.utils.RedisUtil;
import com.example.volunteer.utils.SerialUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private CommentResponseDao commentResponseDao;

    @Autowired
    private CommentPictureDao commentPictureDao;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private OSSUtil ossUtil;

    @Override
    public Response<Boolean> addComment(long userId, String commentText,MultipartFile[] commentPicture){
        Response<Boolean> response=new Response<>();
           Comment comment = new Comment();
           comment.setCommentPublisher(userId);
           comment.setCommentText(commentText);
           comment.setCommentLike(0);
            boolean result = commentDao.addComment(comment) > 0;
            if (!result) {
                logger.error("[addComment Fail], userId: {}", SerialUtil.toJsonStr(userId));
                response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
                return response;
            }
            long commentId = commentDao.findCommentIdByUserId(userId);

        String bucketName = "comment-picture-file-model";
        String filename = "comment_picture"+commentId+"/";
        for(MultipartFile file : commentPicture) {
            String url = ossUtil.uploadFile(bucketName, file, filename+file.getOriginalFilename());
            if (StringUtils.isBlank(url)) {
                logger.error("[addActivitySignFileModel Fail], file: {}", SerialUtil.toJsonStr(file.getOriginalFilename()));
                response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
                return response;
            }
            CommentPicture commentPic = new CommentPicture();
            commentPic.setCommentId(commentId);
            commentPic.setFileName(file.getOriginalFilename());
            commentPic.setPictureUrl(url);
            boolean result2 = commentPictureDao.addCommentPicture(commentPic) > 0;
            if(!result2){
                logger.error("[addCommentPicture Fail], commentPic: {}", SerialUtil.toJsonStr(commentPic));
                response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
                return response;
            }
        }

        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Boolean> updateCommentLikeNumber(long commentId){
        Response<Boolean> response=new Response<>();
        long commentLike = commentDao.getCommentLikeByCommentId(commentId);
        commentLike += 1;
        boolean result = commentDao.updateCommentLike(commentLike, commentId) > 0;
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
    public Response<List<CommentDTO>> getCommentByPublisher(long publisherId){
        Response<List<CommentDTO>> response=new Response<>();

        List<Comment> commentList = commentDao.findCommentById(publisherId);
        if (commentList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_PUBLISHER_NOT_FOUND);
        }
        else
        {
            List <CommentDTO> commentDTOList = new ArrayList<>();
            for(Comment comment : commentList)
                commentDTOList.add(transformComment2CommentDTO(comment));
            response.setSuc(commentDTOList);
        }
        return response;
    }

    @Override
    public Response<List<CommentDTO>> getCommentInOneWeek(){
        Response<List<CommentDTO>> response=new Response<>();

        List<Comment> commentList = commentDao.findCommentInOneWeek();
        if (commentList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        }
        else{
            List <CommentDTO> commentDTOList = new ArrayList<>();
            for(Comment comment : commentList)
                commentDTOList.add(transformComment2CommentDTO(comment));
            response.setSuc(commentDTOList);
        }
        return response;
    }

    @Override
    public Response<List<CommentDTO>> getCommentByRelativeText(String relativeText){
        Response<List<CommentDTO>> response=new Response<>();

        List<Comment> commentList = commentDao.findCommentByText(relativeText);
        if (commentList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_RELATIVE_TEXT_NOT_FOUND);
        }
        else
        {
            List <CommentDTO> commentDTOList = new ArrayList<>();
            for(Comment comment : commentList)
                commentDTOList.add(transformComment2CommentDTO(comment));
            response.setSuc(commentDTOList);
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

    @Override
    public Response<Boolean> addCommentPicture(long commentId, MultipartFile[] commentPicture) {
            Response<Boolean> response=new Response<>();

            boolean result;
            String bucketName = "comment-picture-file-model";
            String filename = "comment_picture"+commentId+"/";
            for(MultipartFile file : commentPicture) {
                String url = ossUtil.uploadFile(bucketName, file, filename+file.getOriginalFilename());
                if (StringUtils.isBlank(url)) {
                    logger.error("[addActivitySignFileModel Fail], file: {}", SerialUtil.toJsonStr(file.getOriginalFilename()));
                    response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
                    return response;
                }
                CommentPicture commentPic = new CommentPicture();
                commentPic.setCommentId(commentId);
                commentPic.setFileName(file.getOriginalFilename());
                commentPic.setPictureUrl(url);
                result= commentPictureDao.addCommentPicture(commentPic) > 0;
                if(!result){
                    logger.error("[addCommentPicture Fail], commentPic: {}", SerialUtil.toJsonStr(commentPic));
                    response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
                    return response;
                }
            }
            response.setSuc(true);
            return response;
        }
    @Override
    public Response<List<CommentDTO>> getCommentByNumber(long number){
        Response<List<CommentDTO>> response=new Response<>();
        List<Comment> commentList = commentDao.findCommentByNumber(number);
        List <CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment : commentList)
            commentDTOList.add(transformComment2CommentDTO(comment));
        response.setSuc(commentDTOList);
        return response;
    }
    private CommentDTO transformComment2CommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setCommentId(comment.getCommentId());
        commentDTO.setCommentDate(comment.getCommentDate());
        commentDTO.setCommentLike(comment.getCommentLike());
        commentDTO.setCommentText(comment.getCommentText());
        commentDTO.setCommentPublisher(userInfoDao.getUserInfoByUserId(comment.getCommentPublisher()));

        commentDTO.setCommentPictureList(commentPictureDao.getCommentPictureByCommentId(comment.getCommentId()));
        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        for(CommentResponse commentResponse:commentResponseDao.findCommentResponseByCommentId(comment.getCommentId(),0L))
            commentResponseDTOList.add(transformCommentResponse2CommentResponseDTO(commentResponse));
        commentDTO.setCommentResponseList(commentResponseDTOList);
        return commentDTO;
    }

    private CommentResponseDTO transformCommentResponse2CommentResponseDTO(CommentResponse commentResponse){
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setCommentId(commentResponse.getCommentId());
        commentResponseDTO.setResponseId(commentResponse.getResponseId());
        commentResponseDTO.setResponseDate(commentResponse.getResponseDate());
        commentResponseDTO.setResponseLike(commentResponse.getResponseLike());
        commentResponseDTO.setResponseText(commentResponse.getResponseText());
        commentResponseDTO.setResponseType(commentResponse.getResponseType());
        commentResponseDTO.setResponsePublisher(userInfoDao.getUserInfoByUserId(commentResponse.getResponsePublisher()));
        return commentResponseDTO;
    }
}
