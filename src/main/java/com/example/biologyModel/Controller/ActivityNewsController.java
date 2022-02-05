package com.example.biologyModel.Controller;

import com.example.biologyModel.DTO.ActivityNewsDTO;
import com.example.biologyModel.Entity.ActivityNews;
import com.example.biologyModel.Exception.VolunteerRuntimeException;
import com.example.biologyModel.Response.Response;
import com.example.biologyModel.Service.ActivityNewsService;
import com.example.biologyModel.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "活动新闻Controller")
@RestController
@RequestMapping("activityNews")
public class ActivityNewsController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ActivityNewsController.class);

    @Autowired
    private ActivityNewsService activityNewsService;


    @GetMapping("/getActivityNewsByActivityId")
    @ApiOperation("获得活动 By Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", paramType = "query", dataType = "long"),
    })
    public Response<List<ActivityNewsDTO>> getActivityNewsById(@RequestParam("activityId") long activityId) {
        Response<List<ActivityNewsDTO>> response = new Response<>();
        try {
            return activityNewsService.getActivityNewsByActivityId(activityId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityNewsByActivityId Illegal Argument], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityNewsByActivityId Runtime Exception], activityId: {}", activityId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityNewsByActivityId Exception], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getActivityNewsByPublisherId")
    @ApiOperation("获得活动 By 活动发布者Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "publisherId", value = "活动发布者id", paramType = "query", dataType = "long"),
    })
    public Response<List<ActivityNewsDTO>> getActivityNewsByPublisherId(@RequestParam("publisherId") long publisherId) {
        Response<List<ActivityNewsDTO>> response = new Response<>();
        try {
            return activityNewsService.getActivityNewsByPublisherId(publisherId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityNewsByPublisherId Illegal Argument], publisherId: {}", publisherId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityNewsByPublisherId Runtime Exception], publisherId: {}", publisherId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityNewsByPublisherId Exception], publisherId: {}", publisherId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getActivityNewsInOneWeek")
    @ApiOperation("获得最近一周的活动")
    @ApiImplicitParams({})
    public Response<List<ActivityNewsDTO>> getActivityNewsInOneWeek() {
        Response<List<ActivityNewsDTO>> response = new Response<>();
        try {
            return activityNewsService.getActivityNewsInOneWeek();
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityNewsInOneWeek Illegal Argument]", e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityNewsInOneWeek Runtime Exception]", e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityNewsInOneWeek Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getActivityNewsByRelativeText")
    @ApiOperation("根据相关内容获得活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "relativeText", value = "相关内容", paramType = "query", dataType = "long"),
    })
    public Response<List<ActivityNewsDTO>> getActivityNewsByRelativeText(@RequestParam("relativeText") String relativeText) {
        Response<List<ActivityNewsDTO>> response = new Response<>();
        try {
            return activityNewsService.getActivityNewsByRelativeText(relativeText);
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityNewsByRelativeText Illegal Argument], relativeText: {}", relativeText, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityNewsByRelativeText Runtime Exception], relativeText: {}", relativeText, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityNewsByRelativeText Exception], relativeText: {}", relativeText, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @PostMapping("/addActivityNews")
    @ApiOperation("添加活动新闻")
    public Response<Boolean> addActivityNews(@RequestBody ActivityNews activityNews) {
        Response<Boolean> response = new Response<>();
        long userId = getUserId();
        try {
            return activityNewsService.addActivityNews(userId,activityNews);
        } catch (IllegalArgumentException e) {
            logger.warn("[addActivityNews Illegal Argument], activityNews: {}", activityNews, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[addActivityNews Runtime Exception], activityNews: {}", activityNews, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[addActivityNews Exception], activityNews: {}", activityNews, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateActivityNewsContentByNewsId")
    @ApiOperation("更新活动新闻By newsId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newsId", value = "活动新闻id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> updateActivityNewsContentByNewsId(@RequestParam("activityNewsContent")MultipartFile activityNewsContent,@RequestParam("newsId") long newsId) {
        Response<Boolean> response = new Response<>();
        try {
            return activityNewsService.updateActivityNewsContent(activityNewsContent,newsId);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateActivityNewsContentByNewsId Illegal Argument], : newsId {}", newsId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateActivityNewsContentByNewsId Runtime Exception], : newsId {}", newsId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateActivityNewsContentByNewsId Exception], :newsId {}", newsId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateActivityNewsTitleByNewsId")
    @ApiOperation("更新活动标题By newsId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newsId", value = "活动新闻id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> updateActivityTitleContentByNewsId(@RequestParam("activityNewsTitle")String activityNewsTitle,@RequestParam("newsId") long newsId) {
        Response<Boolean> response = new Response<>();
        try {
            return activityNewsService.updateActivityNewsTitle(activityNewsTitle,newsId);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateActivityNewsTitleByNewsId Illegal Argument], : newsId {}", newsId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateActivityNewsTitleByNewsId Runtime Exception], : newsId {}", newsId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateActivityNewsTitleByNewsId Exception], :newsId {}", newsId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateActivityNewsPictureByNewsId")
    @ApiOperation("更新活动图片By newsId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newsId", value = "活动新闻id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> updateActivityPictureContentByNewsId(@RequestParam("activityNewsPicture") MultipartFile activityNewsPicture, @RequestParam("newsId") long newsId) {
        Response<Boolean> response = new Response<>();
        try {
            return activityNewsService.updateActivityNewsPicture(activityNewsPicture,newsId);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateActivityNewsPictureByNewsId Illegal Argument], : newsId {}", newsId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateActivityNewsPictureByNewsId Runtime Exception], : newsId {}", newsId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateActivityNewsPictureByNewsId Exception], :newsId {}", newsId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/deleteActivityNewsByNewsId")
    @ApiOperation("删除活动新闻By newsId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newsId", value = "活动新闻id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> deleteActivityNewsByNewsId(@RequestParam("newsId") long newsId) {
        Response<Boolean> response = new Response<>();
        try {
            return activityNewsService.deleteActivityNewsById(newsId);
        } catch (IllegalArgumentException e) {
            logger.warn("[deleteActivityNewsByNewsId Illegal Argument], newsId: {}", newsId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[deleteActivityNewsByNewsId Runtime Exception], newsId: {}", newsId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[deleteActivityNewsByNewsId Exception], newsId: {}", newsId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }@GetMapping("/getActivityNewsByNumber")
    @ApiOperation("获得活动 byNumber")

    public Response<List<ActivityNewsDTO>> getActivityNewsByNumber(@RequestParam("number") long number) {
        Response<List<ActivityNewsDTO>> response = new Response<>();
        try {
            return activityNewsService.getActivityNewsByNumber(number);
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityNewsByNumber Illegal Argument], number: {}", number, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityNewsByNumber Runtime Exception], number: {}", number, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityNewsByNumber Exception], number: {}", number, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

}
