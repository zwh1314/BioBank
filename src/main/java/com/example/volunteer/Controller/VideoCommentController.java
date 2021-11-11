package com.example.volunteer.Controller;

import com.example.volunteer.Entity.VideoComment;
import com.example.volunteer.Exception.VolunteerRuntimeException;
import com.example.volunteer.Request.VideoCommentRequest;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.VideoCommentService;
import com.example.volunteer.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "视频评论Controller")
@RestController
@RequestMapping("videoComment")
public class VideoCommentController {
    private static final Logger logger = LoggerFactory.getLogger(VideoCommentController.class);

    @Autowired
    private VideoCommentService videoCommentService;

    @GetMapping("/getVideoCommentByPublisher")
    @ApiOperation("获得视频评论by发布者Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "publisherId", value = "视频发布者", paramType = "query", dataType = "long"),
    })
    public Response<List<VideoComment>> getVideoCommentByPublisher(@RequestParam("publisherId") long publisherId) {
        Response<List<VideoComment>> response = new Response<>();
        try {
            return videoCommentService.getVideoCommentByPublisher(publisherId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getVideoCommentByPublisher Illegal Argument], publisherId: {}", publisherId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getVideoCommentByPublisher Runtime Exception], publisherId: {}", publisherId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getVideoCommentByPublisher Exception], publisherId: {}", publisherId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getVideoCommentByRelativeText")
    @ApiOperation("获得视频评论by RelativeText")

    public Response<List<VideoComment>> getVideoCommentByRelativeText(@RequestParam("relativeText") String relativeText) {
        Response<List<VideoComment>> response = new Response<>();
        try {
            return videoCommentService.getVideoCommentByRelativeText(relativeText);
        } catch (IllegalArgumentException e) {
            logger.warn("[getVideoCommentByRelativeText Illegal Argument], relativeText: {}", relativeText, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getVideoCommentByRelativeText Runtime Exception], relativeText: {}", relativeText, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getVideoCommentByRelativeText Exception], relativeText: {}", relativeText, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @GetMapping("/getVideoCommentInOneWeek")
    @ApiOperation("获得一周视频评论")
    public Response<List<VideoComment>> getVideoCommentInOneWeek() {
        Response<List<VideoComment>> response = new Response<>();
        try {
            return videoCommentService.getVideoCommentInOneWeek();
        } catch (VolunteerRuntimeException e) {
            logger.error("[getVideoCommentInOneWeek Runtime Exception]",  e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getVideoCommentInOneWeek Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @PostMapping("/addVideoComment")
    @ApiOperation("添加视频评论")
    public Response<Boolean> addVideoComment(@RequestBody VideoCommentRequest videoCommentRequest) {
        Response<Boolean> response = new Response<>();
        try {
            return videoCommentService.addVideoComment(videoCommentRequest);
        } catch (IllegalArgumentException e) {
            logger.warn("[addVideoComment Illegal Argument], videoCommentRequest: {}", videoCommentRequest, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[addVideoComment Runtime Exception], videoCommentRequest: {}", videoCommentRequest, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[addVideoComment Exception], videoCommentRequest: {}", videoCommentRequest, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateVideoCommentLikeNumber")
    @ApiOperation("更新视频评论获赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean>updateVideoCommentLikeNumber(@RequestParam("commentId") long commentId) {
        Response<Boolean> response = new Response<>();
        try {
            return videoCommentService.updateVideoCommentLikeNumber(commentId);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateVideoCommentLikeNumber Illegal Argument], : commentId {}", commentId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateVideoCommentLikeNumber Runtime Exception], : commentId {}", commentId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("[updateVideoCommentLikeNumber Exception], :commentId {}", commentId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @PostMapping("/updateVideoCommentText")
    @ApiOperation("更新视频评论文本")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> updateVideoCommentText(@RequestParam("VideoCommentText")String VideoCommentText,@RequestParam("commentId") long commentId) {
        Response<Boolean> response = new Response<>();
        try {
            return videoCommentService.updateVideoCommentText(VideoCommentText, commentId);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateVideoCommentText Illegal Argument], : commentId {}", commentId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateVideoCommentText Runtime Exception], : commentId {}", commentId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("[updateVideoCommentText Exception], :commentId {}", commentId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }



    @PostMapping("/deleteVideoCommentById")
    @ApiOperation("删除视频评论By videoCommentId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoCommentId", value = "视频评论id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> deleteVideoCommentById(@RequestParam("videoCommentId") long videoCommentId) {
        Response<Boolean> response = new Response<>();
        try {
            return videoCommentService.deleteVideoCommentById(videoCommentId);
        } catch (IllegalArgumentException e) {
            logger.warn("[deleteVideoCommentById Illegal Argument], videoId: {}", videoCommentId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[deleteVideoCommentById Runtime Exception], videoId: {}", videoCommentId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[deleteVideoCommentById Exception], commentId: {}", videoCommentId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
    /*
    @GetMapping("/getCommentLikeByCommentId")
    @ApiOperation("通过commentId查询视频评论点赞")
    public Response<Long> getCommentLikeByCommentId(@RequestParam("commentId") long commentId) {
        Response<Long> response = new Response<>();
        try {
            return videoCommentRedisService.getCommentLikeByCommentId(commentId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getCommentLikeByCommentId Illegal Argument], commentId: {}", commentId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getCommentLikeByCommentId Runtime Exception],commentId: {}",commentId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getCommentLikeByCommentId Exception], commentId: {}", commentId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }

    }

    @PostMapping("likesComment")
    @ApiOperation("点赞视频评论")
    public Response<Boolean> likesComment(@RequestParam("commentId") long commentId){
        Response<Boolean> response = new Response<>();
        try {
            return   videoCommentRedisService. likesVideoComment(commentId);
        } catch (IllegalArgumentException e) {
            logger.warn("[likesComment Illegal Argument], : commentId {}", commentId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[likesComment Runtime Exception], : commentId {}", commentId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("[likesComment Exception], :commentId {}", commentId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }

    }
     */
}
