package com.example.volunteer.Service.ServiceImpl;

import com.example.volunteer.DTO.ActivityDTO;
import com.example.volunteer.Dao.ActivitySignFileDao;
import com.example.volunteer.Dao.ActivityUserDao;
import com.example.volunteer.Entity.Activity;
import com.example.volunteer.Entity.ActivitySignFile;
import com.example.volunteer.Entity.ActivityUser;
import com.example.volunteer.Request.ActivityUserRequest;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.ActivityUserService;
import com.example.volunteer.enums.ResponseEnum;
import com.example.volunteer.utils.OSSUtil;
import com.example.volunteer.utils.SerialUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class ActivityUserServiceImpl implements ActivityUserService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityNewsServiceImpl.class);

    @Autowired
    private ActivityUserDao activityUserDao;

    @Autowired
    private ActivitySignFileDao activitySignFileDao;

    @Autowired
    private OSSUtil ossUtil;

    @Override
    public Response<Boolean> addActivityUser(ActivityUserRequest activityUserRequest) {
        Response<Boolean> response=new Response<>();
        for(ActivityUser activityUser:activityUserRequest.getActivityUserList()) {
            boolean result = activityUserDao.addActivityUser(activityUser) > 0;
            if (!result) {
                logger.error("[addActivityUser Fail], request: {}", SerialUtil.toJsonStr(activityUserRequest));
                response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
                return response;
            }
        }

        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Boolean> updateFormStatusByUserId(String formStatus, long userId) {
        Response<Boolean> response=new Response<>();

        boolean result = activityUserDao.updateFormStatusByUserId(formStatus, userId) > 0;
        if(!result){
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else
        {
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Boolean> updateFormDateByUserId(Date formDate, long userId) {
        Response<Boolean> response=new Response<>();

        boolean result = activityUserDao.updateFormDateByUserId(formDate, userId) > 0;
        if(!result){
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<List<ActivityUser>> getActivityUserByUserId(long userId) {
        Response<List<ActivityUser>> response=new Response<>();

        List<ActivityUser> activityUserList = activityUserDao.findActivityUserByUserId(userId);
        if (activityUserList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_RELATIVE_TEXT_NOT_FOUND);
        }
        else{
            response.setSuc(activityUserList);
        }
        return response;
    }

    @Override
    public Response<List<ActivityUser>> getActivityUserByActivityId(long activityId) {
        Response<List<ActivityUser>> response=new Response<>();

        List<ActivityUser> activityUserList = activityUserDao.findActivityUserByActivityId(activityId);
        if (activityUserList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_RELATIVE_TEXT_NOT_FOUND);
        }
        else{
            response.setSuc(activityUserList);
        }
        return response;
    }

    @Override
    public Response<Boolean> deleteActivityUserByActivityId(long activityId) {
        Response<Boolean> response=new Response<>();

        boolean result=activityUserDao.deleteActivityUserByActivityId(activityId) > 0;
        if(!result){
            logger.error("[deleteActivityUserByActivityId Fail], activityId: {}", SerialUtil.toJsonStr(activityId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Boolean> deleteActivityUserByUserId(long userId) {
        Response<Boolean> response=new Response<>();

        boolean result=activityUserDao.deleteActivityUserByUserId(userId) > 0;
        if(!result){
            logger.error("[deleteActivityUserByUserId Fail], userId: {}", SerialUtil.toJsonStr(userId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<List<ActivitySignFile>> getSignFile(long activityId) {
        Response<List<ActivitySignFile>> response=new Response<>();
        List<ActivitySignFile> activitySignFileList;

        activitySignFileList = activitySignFileDao.getActivitySignFileByActivityId(activityId);
        if(CollectionUtils.isEmpty(activitySignFileList)){
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            return response;
        }
        else{
            response.setSuc(activitySignFileList);
        }
        return response;
    }

    @Override
    public Response<Boolean> uploadSignFile(long userId, long activityId, MultipartFile[] signFile) {
        Response<Boolean> response=new Response<>();

        boolean result;
        String bucketName = "sign-file";
        String filename = "activity_"+activityId+"/";
        for(MultipartFile file : signFile) {
            String url = ossUtil.uploadFile(bucketName, file, filename+file.getOriginalFilename());
            if (StringUtils.isBlank(url)) {
                logger.error("[uploadSignFile Fail], file: {}", SerialUtil.toJsonStr(file.getOriginalFilename()));
                response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
                return response;
            }
            ActivitySignFile activitySignFile = new ActivitySignFile();
            activitySignFile.setActivityId(activityId);
            activitySignFile.setUserId(userId);
            activitySignFile.setFileName(file.getOriginalFilename());
            activitySignFile.setFileUrl(url);
            result=activitySignFileDao.addActivitySignFile(activitySignFile) > 0;
            if(!result){
                logger.error("[uploadSignFile Fail], signFile: {}", SerialUtil.toJsonStr(signFile));
                response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
                return response;
            }
        }

        response.setSuc(true);
        return response;
    }

    @Override
    public Response<List<ActivityDTO>> getFocusedByUserId(long userId) {
        Response<List<ActivityDTO>> response=new Response<>();
        List<ActivityDTO> activityFocusedList = activityUserDao.getFocusedByUserId(userId);

        if(activityFocusedList.size() == 0){
            response.setFail(ResponseEnum.NO_ACTIVITY_FOCUS);
        }else{
            response.setSuc(activityFocusedList);

        }

        return response;
    }
}
