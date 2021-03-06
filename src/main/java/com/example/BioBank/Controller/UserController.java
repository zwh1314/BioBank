package com.example.BioBank.Controller;

import com.example.BioBank.enums.ResponseEnum;
import com.example.BioBank.Exception.BioBankRuntimeException;
import com.example.BioBank.Response.Response;
import com.example.BioBank.Service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(tags = "用户Controller")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/signUpByTel")
    @ApiOperation("通过手机号注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", paramType = "query", dataType = "String"),
    })
    @ApiResponse(code = 200, message = "成功", response = Boolean.class)
    public Response<Boolean> signUp(@RequestParam("tel") String tel,
                                        HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        Response<Boolean> response = new Response<>();
        try {
            validateUserTel(tel);
            return userService.signUpByTel(tel,servletRequest,servletResponse);
        } catch (IllegalArgumentException e) {
            logger.warn("[signUpByTel Illegal Argument], tel: {}", tel,  e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[signUpByTel Runtime Exception], tel: {}", tel, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[signUpByTel Exception], tel: {}", tel, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/signInByTelAndVerifyCode")
    @ApiOperation("通过手机号和验证码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", paramType = "query", dataType = "String"),
    })
    @ApiResponse(code = 200, message = "成功", response = Boolean.class)
    public Response<Boolean> signInByTelAndVerifyCode(@RequestParam("tel") String tel, @RequestParam("verifyCode") String verifyCode,
                                    HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        Response<Boolean> response = new Response<>();
        try {
            validateUserTel(tel);
            return userService.signInByTel(tel,verifyCode,servletRequest,servletResponse);
        } catch (IllegalArgumentException e) {
            logger.warn("[signInByTelAndVerifyCode Illegal Argument], tel: {}, verifyCode: {}", tel, verifyCode, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[signInByTelAndVerifyCode Runtime Exception], tel: {}, verifyCode: {}", tel, verifyCode, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[signInByTelAndVerifyCode Exception], tel: {}, verifyCode: {}", tel, verifyCode, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/signInByTelAndPassword")
    @ApiOperation("通过手机号密码登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataType = "String")
    })
    @ApiResponse(code = 200, message = "成功", response = Boolean.class)
    public Response<Boolean> signInByTelAndPassword(@RequestParam("tel") String tel,@RequestParam("password") String password,
                                    HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        Response<Boolean> response = new Response<>();
        try {
            validateUserTel(tel);
            return userService.signIn(tel,password,servletRequest,servletResponse);
        } catch (IllegalArgumentException e) {
            logger.warn("[signInByTelAndPassword Illegal Argument], tel: {}", tel,  e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[signInByTelAndPassword Runtime Exception], tel: {}", tel, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[signInByTelAndPassword Exception], tel: {}", tel, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updatePassword")
    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", paramType = "query", dataType = "String"),
    })
    public Response<Boolean> updatePassword(@RequestParam("tel") String tel,
                                            @RequestParam("newPassword") String newPassword) {
        Response<Boolean> response = new Response<>();
        try {
            validateUserTelAndPassword(tel,newPassword);
            return userService.updatePassword(tel, newPassword);
        } catch (IllegalArgumentException e) {
            logger.warn("[updatePassword Illegal Argument], tel: {}, newPassword: {}", tel, newPassword, e);
            response.setFail(400,e.getMessage());
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[updatePassword Runtime Exception], tel: {}, newPassword: {}", tel, newPassword, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("[updatePassword Exception], tel: {}, newPassword: {}", tel, newPassword, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }




    @PostMapping("/verifyVerification")
    @ApiOperation("验证验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", paramType = "query", dataType = "String")

    })
    public Response<Boolean> verifyVerification(@RequestParam("tel")String tel,
                                                @RequestParam("verifyCode")String verifyCode){

        return userService.verifyVerification(tel,verifyCode);
    }


    @GetMapping("/getVerifyCode")
    @ApiOperation("获取短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", paramType = "query", dataType = "String"),
    })
    public Response<Boolean> getVerifyCode(@RequestParam("tel") String tel) {
        Response<Boolean> response = new Response<>();

        try {
            validateBaseTel(tel);

            return userService.getVerifyMsgCode(tel);
        } catch (IllegalArgumentException e) {
            logger.warn("[getVerifyCode Illegal Argument], tel: {}", tel, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getVerifyCode Runtime Exception], tel: {}", tel, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("[getVerifyCode Exception], tel: {}", tel, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @PostMapping("/deleteUserByUserId")
    @ApiOperation("删除用户By userId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delete_userId", value = "被删除账户id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> deleteUserInfoByUserId(@RequestParam("delete_userId") long delete_userId) {
        Response<Boolean> response = new Response<>();
        try {
            //执行删除操作的用户id
            long userId = getUserId();
            validateUserId(userId);
            validateUserId(delete_userId);
            return userService.deleteUserByUserId(userId,delete_userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[deleteUserByUserId Illegal Argument], delete_userId: {}", delete_userId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[deleteUserByUserId Runtime Exception], delete_userId: {}", delete_userId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[deleteUserByUserId Exception], delete_userId: {}", delete_userId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getMailVerifyCode")
    @ApiOperation("获取邮件验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mail", value = "邮箱地址", paramType = "query", dataType = "String"),
    })
    public Response<Boolean> getMailVerifyCode(@RequestParam("mail") String mail) {
        Response<Boolean> response = new Response<>();

        try {
            validateBaseMail(mail);

            return userService.getMailVerifyMsgCode(mail);
        } catch (IllegalArgumentException e) {
            logger.warn("[getMailVerifyCode Illegal Argument], mail: {}", mail, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getMailVerifyCode Runtime Exception], mail: {}", mail, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("[getMailVerifyCode Exception], mail: {}", mail, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    private void validateBaseTel(String tel) {
        validateUserTel(tel);
    }

    private void validateBaseMail(String mail) {
        validateUserMail(mail);
    }


    private void validateUserTelAndPassword(String tel, String newPassword) {
        validateUserTel(tel);
        validateUserPassword(newPassword);
    }
}
