package com.example.biologyModel.test;

import com.example.biologyModel.Entity.UserInfo;
import com.example.biologyModel.Request.UserInfoRequest;
import com.example.biologyModel.Service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoTest {
    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void userInfoTest(){
        UserInfoRequest  userInfoRequest=new UserInfoRequest();
        List<UserInfo> list=new ArrayList<>();
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(1L);
        userInfo.setUserName("张文瀚");
        userInfo.setAddress("攀枝花");
        //userInfo.setFax("123");
        userInfo.setPriority("管理员");
        userInfo.setTel("15082361803");
        userInfo.setIntroduction("拨开云雾见太阳");
        userInfo.setHeadPicture("1314");
        userInfo.setMailAddress("2439499577@qq.com");
        userInfo.setQq("2439499577");
        userInfo.setCredits(321);
        list.add(userInfo);
        userInfoRequest.setUserInfoList(list);
        userInfoService.addUserInfo(userInfoRequest);
        System.out.println(userInfoService.getCreditsByUserId(1L));
        System.out.println(userInfoService.deleteUserInfoByUserId(1L));
//        System.out.println(userInfoService.getUserInfoByUserId(1L));
//        userInfo.setUserId(4L);
//        userInfoService.updateUserInfo(userInfoRequest);
//        System.out.println(userInfoService.getUserInfoByUserId(1L));

//        userInfoService.deleteUserInfoByUserId(3L);
    }
}
