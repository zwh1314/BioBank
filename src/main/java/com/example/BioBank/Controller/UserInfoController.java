package com.example.BioBank.Controller;

import com.example.BioBank.DTO.UserInfoDTO;
import com.example.BioBank.Entity.UserInfo;
import com.example.BioBank.Exception.VolunteerRuntimeException;
import com.example.BioBank.Request.UserInfoRequest;
import com.example.BioBank.Response.Response;
import com.example.BioBank.enums.ResponseEnum;
import com.example.BioBank.Service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "用户信息Controller")
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/getUserInfoByUserId")
    @ApiOperation("获得用户信息By userId")
    @ApiImplicitParams({
    })
    public Response<UserInfoDTO> getUserInfoByUserId() {
        Response<UserInfoDTO> response = new Response<>();
        long userId = getUserId();
        try {
            validateUserId(userId);
            return userInfoService.getUserInfoByUserId(userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getUserInfoByUserId Illegal Argument], userId: {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getUserInfoByUserId Runtime Exception], userId: {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getUserInfoByUserId Exception], userId: {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
    @GetMapping("/getUserNameByUserId")
    @ApiOperation("获得用户名By userId")
    @ApiImplicitParams({
    })
    public Response<String> getUserNameByUserId(@RequestParam long userId) {
        Response<String> response = new Response<>();
        try {
            validateUserId(userId);
            return userInfoService.getUserNameByUserId(userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getUserNameByUserId Illegal Argument], userId: {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getUserNameByUserId Runtime Exception], userId: {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getUserNameByUserId Exception], userId: {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/addUserInfo")
    @ApiOperation("添加用户信息")
    public Response<Boolean> addUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        Response<Boolean> response = new Response<>();
        try {
            //validateUserInfoRequest(userInfoRequest);
            return userInfoService.addUserInfo(userInfoRequest);
        } catch (IllegalArgumentException e) {
            logger.warn("[addUserInfo Illegal Argument], userInfoRequest: {}", userInfoRequest, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[addUserInfo Runtime Exception], userInfoRequest: {}", userInfoRequest, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[addUserInfo Exception], userInfoRequest: {}", userInfoRequest, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateHeadPicture")
    @ApiOperation("更新个人头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "headPicture", value = "个人头像图片", paramType = "query", dataType = "MultipartFile"),
    })
    public Response<String> updateHeadPicture(@RequestParam("headPicture") MultipartFile headPicture) {
        Response<String> response = new Response<>();
        long userId = getUserId();
        try {
            validateUserId(userId);
            return userInfoService.updateHeadPicture(userId,headPicture);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateHeadPicture Illegal Argument], headPicture: {}", headPicture, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateHeadPicture Runtime Exception], headPicture: {}", headPicture, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateHeadPicture Exception], headPicture: {}", headPicture, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateUserInfoByUserId")
    @ApiOperation("更新用户信息By userId")
    public Response<Boolean> updateUserInfoByUserId(@RequestBody UserInfo userInfo) {
        Response<Boolean> response = new Response<>();
        long userId = getUserId();
        try {
            userInfo.setUserId(userId);
            validateUserInfo(userInfo);

            return userInfoService.updateUserInfo(userInfo);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateUserInfoByUserId Illegal Argument], userInfo: {}", userInfo, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateUserInfoByUserId Runtime Exception], userInfo: {}", userInfo, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateUserInfoByUserId Exception], userInfo: {}", userInfo, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/deleteUserInfoByUserId")
    @ApiOperation("删除用户信息By userId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "账户id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> deleteUserInfoByUserId(@RequestParam("userId") long userId) {
        Response<Boolean> response = new Response<>();
        try {
            validateUserId(userId);
            return userInfoService.deleteUserInfoByUserId(userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[deleteUserInfoByUserId Illegal Argument], userId: {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[deleteUserInfoByUserId Runtime Exception], userId: {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[deleteUserInfoByUserId Exception], userId: {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    private void validateUserInfo(UserInfo userInfo) {
        Validate.notNull(userInfo);
    }

    @GetMapping("/getCreditsByUserId")
    @ApiOperation("查询用户积分By UserId")
    public Response<Integer> selectCreditsByUserId(){
        Response<Integer> response = new Response<>();
        long userId = getUserId();
        try{
            validateUserId(userId);
            return userInfoService.getCreditsByUserId(userId);
        }catch (IllegalArgumentException e) {
            logger.warn("[getCreditsByUserId Illegal Argument], userId: {}", userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getCreditsByUserId Runtime Exception], userId: {}", userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getCreditsByUserId Exception], userId: {}", userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
}
