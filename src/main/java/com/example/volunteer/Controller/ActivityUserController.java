package com.example.volunteer.Controller;

import com.example.volunteer.DTO.ActivityDTO;
import com.example.volunteer.Entity.ActivitySignFile;
import com.example.volunteer.Entity.ActivityUser;
import com.example.volunteer.Exception.VolunteerRuntimeException;
import com.example.volunteer.Request.ActivityUserRequest;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.ActivityService;
import com.example.volunteer.Service.ActivityUserService;
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


@Api(tags = "活动用户Controller")
@RestController
@RequestMapping("activityUser")
public class ActivityUserController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ActivityUserController.class);

    @Autowired
    private ActivityUserService activityUserService;

    @Autowired
    private ActivityService activityService;


    @GetMapping("/getActivityUserByActivityId")
    @ApiOperation("获得活动用户by 活动Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", paramType = "query", dataType = "long"),
    })
    public Response<List<ActivityUser>> getActivityUserByActivityId(@RequestParam("activityId") long activityId) {
        Response<List<ActivityUser>> response = new Response<>();
        try {
            return activityUserService.getActivityUserByActivityId(activityId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityUserByActivityId Illegal Argument], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityUserByActivityId Runtime Exception], activityId: {}", activityId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityUserByActivityId Exception], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getActivityUserByUserId")
    @ApiOperation("获得活动用户by 用户Id")
    @ApiImplicitParams({})
    public Response<List<ActivityUser>> getActivityUserByUserId() {
        Response<List<ActivityUser>> response = new Response<>();
        long userId = getUserId();
        try {
            validateUserId(userId);

            return activityUserService.getActivityUserByUserId(userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityUserByUserId Illegal Argument], userId: {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityUserByUserId Runtime Exception], userId: {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityUserByUserId Exception], userId: {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getActivityStateByNumber")
    @ApiOperation("获得活动状态by number")
    @ApiImplicitParams({})
    public Response<List<ActivityDTO>> getActivityStateByNumber(@RequestParam("number") long number) {
        Response<List<ActivityDTO>> response = new Response<>();
        long userId = getUserId();
        try {
            validateUserId(userId);
            if(number == 0) {// 已关注
             return activityUserService.getFocusedByUserId(userId);
            }else if(number == 1) {//已报名
                 return activityUserService.getSignedUpActivityByUserId(userId);
            }else if(number == 2){//已发布
                 return activityService.getActivityByOrganizerId(userId);
            }else if(number == 3){//已参加
                 return activityUserService.getParticipatedActivityByUserId(userId);
           }
           else response.setFail(ResponseEnum.ILLEGAL_PARAM);
           return response;
        } catch (IllegalArgumentException e) {
            logger.warn("[getActivityUserByUserId Illegal Argument], userId: {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getActivityUserByUserId Runtime Exception], userId: {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getActivityUserByUserId Exception], userId: {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @PostMapping("/addActivityUser")
    @ApiOperation("添加活动用户")
    public Response<Boolean> addActivityUser(@RequestBody ActivityUserRequest activityUserRequest) {
        Response<Boolean> response = new Response<>();
        try {
            return activityUserService.addActivityUser(activityUserRequest);
        } catch (IllegalArgumentException e) {
            logger.warn("[addActivityUser Illegal Argument], activityUser: {}", activityUserRequest, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[addActivityUser Runtime Exception], activityUser: {}", activityUserRequest, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[addActivityUser Exception], activityUser: {}", activityUserRequest, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateFormStatusByUserId")
    @ApiOperation("更新活动报名表状态")
    @ApiImplicitParams({})
    public Response<Boolean> updateFormStatusByUserId(@RequestParam("formStatus") String formStatus) {
        Response<Boolean> response = new Response<>();
        long userId = getUserId();
        try {
            validateUserId(userId);

            return activityUserService.updateFormStatusByUserId(formStatus,userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateFormStatusByUserId Illegal Argument], : userId {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateFormStatusByUserId Runtime Exception], :userId {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateFormStatusByUserId Exception], :userId {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateFormDateByUserId")
    @ApiOperation("更新交表日期")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formDate", value = "交表日期", paramType = "query", dataType = "String"),
    })
    public Response<Boolean> updateFormDateByUserId(@RequestParam("formDate") String formDate) {
        Response<Boolean> response = new Response<>();
        long userId = getUserId();
        try {
            validateUserId(userId);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date NewFormDate = formatter.parse(formDate, pos);
            return activityUserService.updateFormDateByUserId(NewFormDate,userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateFormDateByUserId Illegal Argument], : userId {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateFormDateByUserId Runtime Exception], :userId {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateFormDateByUserId Exception], :userId {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @PostMapping("/deleteActivityUserByUserId")
    @ApiOperation("删除活动用户userId")
    @ApiImplicitParams({})
    public Response<Boolean> deleteActivityUserByUserId() {
        Response<Boolean> response = new Response<>();
        long userId = getUserId();
        try {
            validateUserId(userId);

            return activityUserService.deleteActivityUserByUserId(userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[deleteActivityUserByUserId Illegal Argument], userId: {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[deleteActivityUserByUserId Runtime Exception], userId: {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[deleteActivityUserByUserId Exception], userId: {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getSignFileByActivityId")
    @ApiOperation("下载报名表By activityId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", paramType = "query", dataType = "long"),
    })
    public Response<List<ActivitySignFile>> getSignFileByActivityId(@RequestParam("activityId") long activityId) {
        Response<List<ActivitySignFile>> response = new Response<>();
        try {
            return activityUserService.getSignFile(activityId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getSignFileByActivityId Illegal Argument], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getSignFileByActivityId Runtime Exception], activityId: {}", activityId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getSignFileByActivityId Exception], activityId: {}", activityId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/uploadSignFileByActivityId")
    @ApiOperation("上传报名表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "signFile", value = "报名表", paramType = "query", dataType = "MultipartFile[]"),
    })
    public Response<Boolean> uploadSignFileByActivityId(@RequestParam("activityId") long activityId, @RequestParam("signFile") MultipartFile[] signFile) {
        Response<Boolean> response = new Response<>();
        long userId;
        try {
            userId = getUserId();
            validateUserId(userId);

            return activityUserService.uploadSignFile(userId,activityId,signFile);
        } catch (IllegalArgumentException e) {
            logger.warn("[uploadSignFileByActivityId Illegal Argument], signFile: {}", SerialUtil.toJsonStr(signFile), e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[uploadSignFileByActivityId Runtime Exception], signFile: {}", SerialUtil.toJsonStr(signFile), e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[uploadSignFileByActivityId Exception], signFile: {}", SerialUtil.toJsonStr(signFile), e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
    @PostMapping("/updateIsFocusByUserId")
    @ApiOperation("更新是否关注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动Id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "isFocus", value = "是否关注", paramType = "query", dataType = "boolean")
    })
    public Response<Boolean> updateIsFocusByUserId(@RequestParam("activityId") long activityId, @RequestParam("isFocus") boolean isFocus) {
        Response<Boolean> response = new Response<>();
        long userId = getUserId();
        try {
            validateUserId(userId);
            return activityUserService.updateActivityIsFocus(activityId, userId, isFocus);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateIsFocusByUserId Illegal Argument], : userId {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateIsFocusByUserId Runtime Exception], :userId {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateIsFocusByUserId Exception], :userId {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


}