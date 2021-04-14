package com.example.volunteer.Service.ServiceImpl;

import com.example.volunteer.DTO.CommentResponseDTO;
import com.example.volunteer.DTO.VideoCommentDTO;
import com.example.volunteer.DTO.VideoDTO;
import com.example.volunteer.Dao.CommentResponseDao;
import com.example.volunteer.Dao.UserInfoDao;
import com.example.volunteer.Dao.VideoCommentDao;
import com.example.volunteer.Dao.VideoDao;
import com.example.volunteer.Entity.CommentResponse;
import com.example.volunteer.Entity.Video;
import com.example.volunteer.Entity.VideoComment;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.VideoService;
import com.example.volunteer.enums.ResponseEnum;
import com.example.volunteer.utils.OSSUtil;
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
public class VideoServiceImpl implements VideoService {
    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private VideoCommentDao videoCommentDao;

    @Autowired
    private CommentResponseDao commentResponseDao;

    @Autowired
    private OSSUtil ossUtil;

    @Override
    public Response<Boolean> addVideo(Video video, MultipartFile video_mp4){
        Response<Boolean> response=new Response<>();

        String bucketName = "videos-url";
        String filename = "videoPublisher_"+video.getVideoPublisher()+"/"+video_mp4.getOriginalFilename();
        String url = ossUtil.uploadFile(bucketName,video_mp4,filename);
        if(StringUtils.isBlank(url)){
            logger.error("[addVideo Fail], video_mp4: {}", SerialUtil.toJsonStr(video_mp4.getOriginalFilename()));
            response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
            return response;
        }
        video.setVideoUrl(url);

        boolean result = videoDao.addVideo(video) > 0;
        if (!result) {
            logger.error("[addVideo Fail], video_mp4: {}", SerialUtil.toJsonStr(video_mp4));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else {
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Boolean> updateVideoTextContent(String textContent, long videoId){
        Response<Boolean> response=new Response<>();

        boolean result = videoDao.updateVideoText(textContent,videoId) > 0;
        if (!result) {
            logger.error("[updateVideoText Fail], videoId: {}", SerialUtil.toJsonStr(videoId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Boolean> updateVideoLikeNumber(long likeNumber, long videoId){
        Response<Boolean> response=new Response<>();

        boolean result = videoDao.updateVideoLike(likeNumber,videoId) > 0;
        if (!result) {
            logger.error("[updateVideoLike Fail], videoId: {}", SerialUtil.toJsonStr(videoId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else {
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<List<VideoDTO>> getVideoByPublisherId(long publisherId){
        Response<List<VideoDTO>> response=new Response<>();

        List<Video> videoList = videoDao.findVideoByUser(publisherId);
        if (videoList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_PUBLISHER_NOT_FOUND);
        }
        else {
            List<VideoDTO> videoDTOList = new ArrayList<>();
            for(Video video:videoList)
                videoDTOList.add(transformVideo2VideoDTO(video));
            response.setSuc(videoDTOList);
        }
        return response;
    }

    @Override
    public Response<List<VideoDTO>> getVideoByRelativeText(String relativeText){
        Response<List<VideoDTO>> response=new Response<>();

        List<Video> videoList = videoDao.findVideoByText(relativeText);
        if (videoList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_RELATIVE_TEXT_NOT_FOUND);
        }
        else {
            List<VideoDTO> videoDTOList = new ArrayList<>();
            for(Video video:videoList)
                videoDTOList.add(transformVideo2VideoDTO(video));
            response.setSuc(videoDTOList);
        }
        return response;
    }

    @Override
    public Response<List<VideoDTO>> getVideoInOneWeek(){
        Response<List<VideoDTO>> response=new Response<>();

        List<Video> videoList = videoDao.findVideoInOneWeek();
        if (videoList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        }
        else {
            List<VideoDTO> videoDTOList = new ArrayList<>();
            for(Video video:videoList)
                videoDTOList.add(transformVideo2VideoDTO(video));
            response.setSuc(videoDTOList);
        }
        return response;
    }

    @Override
    public Response<Boolean> deleteVideoById(long videoId){
        Response<Boolean> response=new Response<>();

        boolean result=videoDao.deleteVideoById(videoId) > 0;
        if(!result){
            logger.error("[deleteVideoById Fail], videoId: {}", SerialUtil.toJsonStr(videoId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }
    @Override
    public Response<List<VideoDTO>> getVideoByNumber(long number){
        Response<List<VideoDTO>> response=new Response<>();

        List<Video> videoList = videoDao.findVideoByNumber(number);
        List<VideoDTO> videoDTOList = new ArrayList<>();
        for(Video video:videoList)
            videoDTOList.add(transformVideo2VideoDTO(video));
        response.setSuc(videoDTOList);
        return response;
    }

    private VideoDTO transformVideo2VideoDTO(Video video){
        VideoDTO videoDTO = new VideoDTO();

        videoDTO.setVideoId(video.getVideoId());
        videoDTO.setVideoDate(video.getVideoDate());
        videoDTO.setVideoFace(video.getVideoFace());
        videoDTO.setVideoLike(video.getVideoLike());
        videoDTO.setVideoUrl(video.getVideoUrl());
        videoDTO.setVideoText(video.getVideoText());
        videoDTO.setVideoTitle(video.getVideoTitle());
        videoDTO.setVideoPlayNum(video.getVideoPlayNum());
        videoDTO.setVideoPublisher(userInfoDao.getUserInfoByUserId(video.getVideoPublisher()));

        List<VideoComment> videoCommentList = videoCommentDao.findCommentByVideoId(video.getVideoId());
        List<VideoCommentDTO> videoCommentDTOList = new ArrayList<>();
        for(VideoComment videoComment:videoCommentList)
            videoCommentDTOList.add(transformVideoComment2VideoCommentDTO(videoComment));
        videoDTO.setVideoCommentDTOList(videoCommentDTOList);
        return videoDTO;
    }

    private VideoCommentDTO transformVideoComment2VideoCommentDTO(VideoComment videoComment){
        VideoCommentDTO videoCommentDTO = new VideoCommentDTO();

        videoCommentDTO.setCommentId(videoComment.getCommentId());
        videoCommentDTO.setVideoId(videoComment.getVideoId());
        videoCommentDTO.setCommentDate(videoComment.getCommentDate());
        videoCommentDTO.setCommentLike(videoComment.getCommentLike());
        videoCommentDTO.setCommentText(videoComment.getCommentText());
        videoCommentDTO.setCommentPublisher(userInfoDao.getUserInfoByUserId(videoComment.getCommentPublisher()));

        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        for(CommentResponse commentResponse:commentResponseDao.findCommentResponseByCommentId(videoComment.getCommentId(),1L))
            commentResponseDTOList.add(transformCommentResponse2CommentResponseDTO(commentResponse));
        videoCommentDTO.setCommentResponseList(commentResponseDTOList);
        return videoCommentDTO;
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
