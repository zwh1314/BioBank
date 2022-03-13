package com.example.BioBank.Service.ServiceImpl;

import com.example.BioBank.DTO.UserDTO;
import com.example.BioBank.Dao.BaseOperateLogDao;
import com.example.BioBank.Dao.UserDao;
import com.example.BioBank.Dao.UserInfoDao;
import com.example.BioBank.DTO.UserInfoDTO;
import com.example.BioBank.Entity.BaseOperateLog;
import com.example.BioBank.Entity.CacheSet;
import com.example.BioBank.Entity.User;
import com.example.BioBank.Entity.UserInfo;
import com.example.BioBank.Exception.BioBankRuntimeException;
import com.example.BioBank.Response.Response;
import com.example.BioBank.enums.UserPriorityEnum;
import com.example.BioBank.enums.ResponseEnum;
import com.example.BioBank.Service.UserInfoService;
import com.example.BioBank.utils.OSSUtil;
import com.example.BioBank.utils.SerialUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BaseOperateLogDao baseOperateLogDao;

    @Autowired
    private OSSUtil ossUtil;

    UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Override
    public Response<UserInfoDTO> getUserInfoByUserId(long userId) {
        Response<UserInfoDTO> response = new Response<>();

        UserInfoDTO userInfoDTO = userInfoDao.getUserInfoByUserId(userId);
        if (userInfoDTO == null) {
            response.setFail(ResponseEnum.USERINFO_NOT_FOUND);
        } else {
            response.setSuc(userInfoDTO);
        }

        return response;
    }

    @Override
    public Response<String> updateHeadPicture(long userId, MultipartFile headPicture) {
        Response<String> response = new Response<>();

        String bucketName = "head-picture";
        String filename = "user_" + userId + "/" + headPicture.getOriginalFilename();
        String url = ossUtil.uploadFile(bucketName, headPicture, filename);
        if (StringUtils.isBlank(url)) {
            logger.error("[updateHeadPicture Fail], headPicture: {}", SerialUtil.toJsonStr(headPicture.getOriginalFilename()));
            response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
            return response;
        }

        boolean result = userInfoDao.updateHeadPicture(userId, url) > 0;
        if (!result) {
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            return response;
        }
        response.setSuc(url);
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> updateUserInfo(UserInfo userInfo) {
        Response<Boolean> response = new Response<>();

        try {
            //同一事务
            userInfoDao.updateUserInfo(userInfo);
            //插入基本操作日志
            BaseOperateLog baseOperateLog = new BaseOperateLog();
            String operateDescription = "用户: " + userInfo.getUserId() + ", 更新了用户信息";
            baseOperateLog.setOperateDescription(operateDescription);
            baseOperateLogDao.insertBaseOperateLog(baseOperateLog);
        } catch (Exception e) {
            logger.warn("[updateUserInfo Update DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Integer> getCreditsByUserId(long userId) {
        Response<Integer> response = new Response<>();

        UserInfoDTO userInfoDTO = userInfoDao.getCreditsById(userId);
        if (null == userInfoDTO) {
            logger.error("[getCreditsById Fail], request:{}", SerialUtil.toJsonStr(userId));
            response.setFail(ResponseEnum.USERINFO_NOT_FOUND);
        } else {
            response.setSuc(userInfoDTO.getCredits());
        }
        return response;
    }

    @Override
    public Response<String> getUserNameByUserId(long userId) {
        Response<String> response = new Response<>();

        String userName = userInfoDao.getUserNameByUserId(userId);
        if (null == userName) {
            logger.error("[getUserNameByUserId Fail], request:{}", SerialUtil.toJsonStr(userId));
            response.setFail(ResponseEnum.USERINFO_NOT_FOUND);
        } else {
            response.setSuc(userName);
        }
        return response;
    }

    @Override
    public Response<Boolean> addUserCredit(long userId) {
        Response<Boolean> response = new Response<>();
        UserInfoDTO userInfoDTO = userInfoDao.getCreditsById(userId);

        if (null == userInfoDTO) {
            logger.error("[getUserNameByUserId Fail], userId:{}", SerialUtil.toJsonStr(userId));
            response.setFail(ResponseEnum.USERINFO_NOT_FOUND);
        } else {
            long credit = userInfoDTO.getCredits();
            credit += 1;
            int result = userInfoDao.updateCredits(userId, credit);
            if (result > 0) response.setSuc(true);
            else response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> updatePriority(long userId, long authorUserId, int newPriority) {
        Response<Boolean> response = new Response<>();
        UserDTO userModified = userDao.getUserByUserId(userId);
        UserDTO userAuthor = userDao.getUserByUserId(authorUserId);
        if (userModified == null) {
            logger.warn("[updatePriority Fail], modifiedUserId:{}", SerialUtil.toJsonStr(userId));
            response.setFail(ResponseEnum.USER_NOT_FOUND);
            return response;
        }
        if (userAuthor.getPriority() <= userModified.getPriority() || newPriority >= userAuthor.getPriority()) {
            logger.warn("[updatePriority Fail], authorUserId:{}", SerialUtil.toJsonStr(authorUserId));
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        try {
            userDao.updatePriority(userId, newPriority);
            userInfoDao.updatePriority(userId, UserPriorityEnum.getPriorityNameByPriority(newPriority));
            //插入基本操作日志
            BaseOperateLog baseOperateLog = new BaseOperateLog();
            String operateDescription = "用户: " + authorUserId + ", 更新了用户: " + userId + ", 的权限为: " + UserPriorityEnum.getPriorityNameByPriority(newPriority);
            baseOperateLog.setOperateDescription(operateDescription);
            baseOperateLogDao.insertBaseOperateLog(baseOperateLog);
        } catch (Exception e) {
            logger.warn("[updatePriority Update DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        //更新完数据库后更新缓存
        User user = userServiceImpl.transformUserDTO2User(userModified);
        user.setPriority(newPriority);
        CacheSet.userCache.put(userModified.getTel(), user);

        response.setSuc(true);
        return response;
    }
}
