package com.example.biologyModel.Service;

import com.example.biologyModel.DTO.UserInfoDTO;
import com.example.biologyModel.Entity.UserInfo;
import com.example.biologyModel.Request.UserInfoRequest;
import com.example.biologyModel.Response.Response;
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
