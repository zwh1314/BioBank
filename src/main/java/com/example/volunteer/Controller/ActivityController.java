package com.example.volunteer.Controller;

import com.example.volunteer.DTO.ActivityDTO;
import com.example.volunteer.Entity.Activity;
import com.example.volunteer.Exception.VolunteerRuntimeException;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.ActivityService;
import com.example.volunteer.enums.ResponseEnum;
import com.example.volunteer.utils.SerialUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Api(tags = "活动Controller")
@RestController
@RequestMapping("activity")
public class ActivityController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;


    @GetMapping("/getActivityByActivityId")
    @ApiOperation("获得活动 by活动Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", paramType = "query", dataType = "long"),
    })
    public Response<ActivityDTO> getActivityByActivityId(@RequestParam("activityId") long activityId) {
        Response<ActivityDTO> response = new Response<>();
        try {
            return activityService.getActivityByActivityId(activityId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityByActivityId Illegal Argument], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityByActivityId Runtime Exception], activityId: {}", activityId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityByActivityId Exception], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @PostMapping("/addActivity")
    @ApiOperation("添加活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activity", value = "活动主体", paramType = "query", dataType = "Activity"),
            @ApiImplicitParam(name = "signFileModel", value = "报名表模板", paramType = "query", dataType = "MultipartFile[]"),
            @ApiImplicitParam(name = "activityPicture", value = "活动图片", paramType = "query", dataType = "MultipartFile[]"),
    })
    public Response<Boolean> addActivity(Activity activity,
                                         @RequestParam(value = "uploadSignFileModel", required = false) MultipartFile[] uploadSignFileModel,
                                         @RequestParam(value = "uploadActivityPicture", required = false) MultipartFile[] uploadActivityPicture) {
        Response<Boolean> response = new Response<>();
        long userId =getUserId();
        try {
            if(uploadSignFileModel==null)
                uploadSignFileModel = new MultipartFile[0];
            if(uploadActivityPicture==null)
                uploadActivityPicture = new MultipartFile[0];
            return activityService.addActivity(userId,activity,uploadSignFileModel,uploadActivityPicture);
        } catch (IllegalArgumentException e) {
            logger.warn("[addActivity Illegal Argument], activity: {}", SerialUtil.toJsonStr(activity), e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[addActivity Runtime Exception], activity: {}", SerialUtil.toJsonStr(activity), e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[addActivity Exception], activity: {}", SerialUtil.toJsonStr(activity), e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateActivity")
    @ApiOperation("更新活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> updateActivity(@RequestParam("activityId")long activityId,
                                            @RequestParam("activityName")String activityName,
                                            @RequestParam("activityContent")String activityContent,
                                            @RequestParam("activityOrganizer")String activityOrganizer,
                                            @RequestParam("activityDate") String activityDate) {
        Response<Boolean> response = new Response<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date NewActivityDate = formatter.parse(activityDate, pos);
        long ActivityOrganizer = Long.parseLong(activityOrganizer);
        Activity activity = new Activity();//(activityName, activityContent,ActivityOrganizer,NewActivityDate);
        activity.setActivityId(activityId);
        activity.setActivityName(activityName);
        activity.setActivityContent(activityContent);
        activity.setActivityDate(NewActivityDate);
        activity.setActivityOrganizer(ActivityOrganizer);
        try {
            return activityService.updateActivity(activity);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateActivity Illegal Argument], : activity {}", activity, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateActivity Runtime Exception], :activity {}", activity, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateActivity Exception], :activity {}", activity, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @PostMapping("/deleteActivityByActivityId")
    @ApiOperation("删除活动By activityId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> deleteActivityByActivityId(@RequestParam("activityId") long activityId) {
        Response<Boolean> response = new Response<>();
        try {
            return activityService.deleteActivityByActivityId(activityId);
        } catch (IllegalArgumentException e) {
            logger.warn("[deleteActivityByActivityId Illegal Argument], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[deleteActivityByActivityId Runtime Exception], activityId: {}", activityId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[deleteActivityByActivityId Exception], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getActivityByNumber")
    @ApiOperation("获得活动by Number")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "需要的活动个数", paramType = "query", dataType = "long"),
    })
    public Response<List<ActivityDTO>> getActivityByNumber(@RequestParam("number") long number) {
        Response<List<ActivityDTO>> response = new Response<>();
        try {
            return activityService.getActivityByNumber(number);
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityByNumber Illegal Argument], number: {}", number, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityByNumber Runtime Exception], number: {}", number, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityByNumber Exception], number: {}", number, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
    @PostMapping("/getActivityByActivityName")
    @ApiOperation("获得活动 by活动名字")
    public Response<List<ActivityDTO>> getActivityByActivityName(@RequestParam("activityName") String activityName) {
        Response<List<ActivityDTO>> response = new Response<>();
        try {
            return activityService.getActivityByActivityName(activityName);
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityByActivityName Illegal Argument], activityName: {}", activityName, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityByActivityName Runtime Exception], activityName: {}", activityName, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityByActivityName Exception], activityName: {}", activityName, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
}
