package com.example.volunteer.Service.ServiceImpl;

import com.example.volunteer.DTO.ActivityDTO;
import com.example.volunteer.Dao.ActivitySignFileDao;
import com.example.volunteer.Dao.ActivityUserDao;
import com.example.volunteer.Entity.ActivitySignFile;
import com.example.volunteer.Entity.ActivityUser;
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
    public Response<List<ActivityDTO>> getSignedUpActivityByUserId(long userId) {
        Response<List<ActivityDTO>> response=new Response<>();

        List<ActivityDTO> ActivityDTOList = activityUserDao.findSignedUpActivityByUserId(userId);
        if (ActivityDTOList.size() == 0) {
            response.setFail(ResponseEnum.SIGNED_ACTIVITY_NOT_FOUND);
        }
        else{
            response.setSuc(ActivityDTOList);
        }
        return response;
    }

    @Override
    public Response<Boolean> updateActivityIsFocus(long activityId, long userId, boolean isFocus) {
        Response<Boolean> response = new Response<>();
        ActivityUser activityUser = activityUserDao.findActivityUserByUserIdAndActivityId(activityId, userId);
        if(activityUser == null){
            ActivityUser activityU = new ActivityUser();
            activityU.setActivityId(activityId);
            activityU.setUserId(userId);
            activityU.setIsFocus(isFocus);
            activityU.setFormStatus(false);
            boolean result = activityUserDao.addActivityUser(activityU) > 0;
           if(!result){
               response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
           }
        }else
        {
          boolean result = activityUserDao.updateIsFocus(activityId,userId,isFocus) > 0;
            if(!result){
                response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            }
        }
        response.setSuc(true);
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
        String bucketName = "sign-files";
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

        if(activityUserDao.findActivityUserByUserIdAndActivityId(activityId,userId)==null) {
            ActivityUser activityUser = new ActivityUser();
            activityUser.setActivityId(activityId);
            activityUser.setUserId(userId);
            activityUser.setFormStatus(true);
            activityUser.setIsFocus(false);
            activityUser.setFormDate(new Date());
            if(activityUserDao.addActivityUser(activityUser) <= 0){
                response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
                return response;
            }
        }
        else{
            if(!(activityUserDao.updateFormStatusByUserIdAndActivityId(activityId,userId,true)>0&&activityUserDao.updateFormDateByUserIdAndActivityId(activityId,userId)>0))
            {
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

    @Override
    public Response<List<ActivityDTO>> getParticipatedActivityByUserId(long userId) {
        Response<List<ActivityDTO>> response=new Response<>();

        List<ActivityDTO> ActivityDTOList = activityUserDao.findParticipatedActivityByUserId(userId);
        if (ActivityDTOList.size() == 0) {
            response.setFail(ResponseEnum.SIGNED_ACTIVITY_NOT_FOUND);
        }
        else{
            response.setSuc(ActivityDTOList);
        }
        return response;
    }
}
