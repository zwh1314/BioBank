package com.example.BioBank.Controller;

import com.example.BioBank.DTO.UserInfoDTO;
import com.example.BioBank.Entity.UserInfo;
import com.example.BioBank.Exception.BioBankRuntimeException;
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
        try {
            long userId = getUserId();
            validateUserId(userId);
            return userInfoService.getUserInfoByUserId(userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getUserInfoByUserId Illegal Argument]", e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getUserInfoByUserId Runtime Exception]", e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getUserInfoByUserId Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
    @GetMapping("/getUserNameByUserId")
    @ApiOperation("获得用户名By userId")
    @ApiImplicitParams({
    })
    public Response<String> getUserNameByUserId() {
        Response<String> response = new Response<>();
        try {
            long userId = getUserId();
            validateUserId(userId);
            return userInfoService.getUserNameByUserId(userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getUserNameByUserId Illegal Argument]", e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getUserNameByUserId Runtime Exception]", e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getUserNameByUserId Exception]", e);
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
        try {
            long userId = getUserId();
            validateUserId(userId);
            return userInfoService.updateHeadPicture(userId,headPicture);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateHeadPicture Illegal Argument], headPicture: {}", headPicture, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
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
        try {
            long userId = getUserId();
            userInfo.setUserId(userId);
            validateUserInfo(userInfo);

            return userInfoService.updateUserInfo(userInfo);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateUserInfoByUserId Illegal Argument], userInfo: {}", userInfo, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[updateUserInfoByUserId Runtime Exception], userInfo: {}", userInfo, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateUserInfoByUserId Exception], userInfo: {}", userInfo, e);
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
        try{
            long userId = getUserId();
            validateUserId(userId);
            return userInfoService.getCreditsByUserId(userId);
        }catch (IllegalArgumentException e) {
            logger.warn("[getCreditsByUserId Illegal Argument]", e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getCreditsByUserId Runtime Exception]", e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getCreditsByUserId Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/updatePriority")
    @ApiOperation("更改用户的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "被修改用户id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "newPriority", value = "用户权限", paramType = "query", dataType = "int")
    })
    public Response<Boolean> updatePriority(@RequestParam("userId") long userId, @RequestParam("newPriority") int newPriority){
        Response<Boolean> response = new Response<>();
        try{
            long authorUserId = getUserId();
            validateUserId(userId);
            validateUserId(authorUserId);
            return userInfoService.updatePriority(userId,authorUserId,newPriority);
        }catch (IllegalArgumentException e) {
            logger.warn("[updatePriority Illegal Argument], userId: {}, newPriority: {}", userId, newPriority, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[updatePriority Runtime Exception], userId: {}, newPriority: {}", userId, newPriority, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updatePriority Exception], userId: {}, newPriority: {}", userId, newPriority, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
}
