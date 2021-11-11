package com.example.volunteer.Service;

import com.example.volunteer.DTO.UserInfoDTO;
import com.example.volunteer.Entity.UserInfo;
import com.example.volunteer.Request.UserInfoRequest;
import com.example.volunteer.Response.Response;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService {
    Response<UserInfoDTO> getUserInfoByUserId(long userId);

    Response<Boolean> addUserInfo(UserInfoRequest userInfoRequest);

    Response<String> updateHeadPicture(long userId, MultipartFile headPicture);

    Response<Boolean> updateUserInfo(UserInfo userInfo);

    Response<Boolean> deleteUserInfoByUserId(long userId);

    Response<Integer> getCreditsByUserId(long userId);

    Response<String> getUserNameByUserId(long userId);

    Response<Boolean>addUserCredit(long userId);



}
