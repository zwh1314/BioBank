package com.example.volunteer.Service;


import com.example.volunteer.DTO.UserInfoDTO;
import com.example.volunteer.Response.Response;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface UserService {

    /**
     * 注册1
     */
    Response<UserInfoDTO>signUpByTel(String tel, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

    /**
     * 登录
     */
    Response<UserInfoDTO> signIn(String tel, String password, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

    /**
     * 登录3
     */
    Response<UserInfoDTO> signInByTel(String tel, String verifyCode, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

    /**
     * 更新密码
     */
    Response<Boolean> updatePassword(String tel, String newPassword);


    /**
     * 验证登录短信验证码
     */
    Response<Boolean> getVerifyMsgCode(String tel);

    /**
     * 验证登录邮箱验证码
     */
    public Response<Boolean> getMailVerifyMsgCode(String mail);

    /**
     *  验证验证码
     */
    Response <Boolean> verifyVerification(String tel, String verifyCode);

    /**
     * 删除用户信息
     */
    Response<Boolean> deleteUserByUserId(long userId);

//    Response<Boolean> updatePasswordByMail(String mail, String oldPassword, String newPassword, String verifyCode);
//
//    Response<Boolean> forgetPasswordByMail(String mail, String newPassword, String verifyCode);
}
