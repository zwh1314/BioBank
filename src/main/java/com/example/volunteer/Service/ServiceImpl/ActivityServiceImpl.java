package com.example.volunteer.Service.ServiceImpl;

import com.example.volunteer.Dao.ActivityDao;
import com.example.volunteer.DTO.ActivityDTO;
import com.example.volunteer.Dao.ActivityPictureDao;
import com.example.volunteer.Dao.ActivitySignFileModelDao;
import com.example.volunteer.Entity.*;
import com.example.volunteer.Response.Response;
import com.example.volunteer.enums.ResponseEnum;

import com.example.volunteer.Service.ActivityService;
import com.example.volunteer.utils.OSSUtil;
import com.example.volunteer.utils.SerialUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityPictureDao activityPictureDao;

    @Autowired
    private ActivitySignFileModelDao activitySignFileModelDao;

    @Autowired
    private OSSUtil ossUtil;

    @Override
    public Response<Boolean> addActivity(long userId, Activity activity, MultipartFile[] signFileModel, MultipartFile[] activityPicture){
        Response<Boolean> response = new Response<>();

        activity.setActivityOrganizer(userId);
        int insertResult = activityDao.insertActivity(activity);
        if (insertResult <= 0) {
            logger.error("[addActivity Fail], activity: {}", SerialUtil.toJsonStr(activity));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            return response;
        }

        if(signFileModel.length>0&&!addActivitySignFileModel(activity.getActivityId(),signFileModel)){
            response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
            return response;
        }
        if(activityPicture.length>0&&!addActivityPicture(activity.getActivityId(),activityPicture)){
            response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
            return response;
        }
        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Boolean> updateActivity(Activity activity) {
        Response<Boolean> response=new Response<>();
        boolean result = activityDao.updateActivity(activity) > 0;
        if(!result){
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<ActivityDTO> getActivityByActivityId(long activityId) {
        Response<ActivityDTO> response=new Response<>();
        ActivityDTO activityDTO = new ActivityDTO();

        Activity activityQueryResult = activityDao.getActivityByActivityId(activityId);
        if (activityQueryResult == null) {
            response.setFail(ResponseEnum.FAIL);
        }
        else {
            convertActivity2ActivityDTO(activityQueryResult,activityDTO);
            activityDTO.setActivitySignFileModelList(activitySignFileModelDao.getActivitySignFileModelByActivityId(activityId));
            activityDTO.setActivityPictureList(activityPictureDao.getActivityPictureByActivityId(activityId));
            response.setSuc(activityDTO);
        }
        return response;
    }

    @Override
    public Response<List<ActivityDTO>> getActivityByOrganizerId(long organizerId) {
        Response<List<ActivityDTO>> response=new Response<>();

        List<Activity> activityList = activityDao.getActivityByOrganizer(organizerId);

        if (activityList == null || activityList.size()==0) {
            response.setFail(ResponseEnum.FAIL);
        }
        else {
            List<ActivityDTO> activityDTOList = new ArrayList<>();
            for(Activity activity :activityList){
                ActivityDTO activityDTO = new ActivityDTO();
                convertActivity2ActivityDTO(activity, activityDTO);
                activityDTO.setActivityPictureList(activityPictureDao.getActivityPictureByActivityId(activity.getActivityId()));
                activityDTO.setActivitySignFileModelList(activitySignFileModelDao.getActivitySignFileModelByActivityId(activity.getActivityId()));
                activityDTOList.add(activityDTO);
            }
            response.setSuc(activityDTOList);
        }
        return response;
    }
    public Response<List<ActivityDTO>> getActivityByActivityName(String activityName) {
        Response<List<ActivityDTO>> response=new Response<>();

        List<Activity> activityList = activityDao.getActivityByActivityName(activityName);

        if (activityList == null || activityList.size()==0) {
            response.setFail(ResponseEnum.FAIL);
        }
        else {
            List<ActivityDTO> activityDTOList = new ArrayList<>();
            for(Activity activity :activityList){
                ActivityDTO activityDTO = new ActivityDTO();
                convertActivity2ActivityDTO(activity, activityDTO);
                activityDTO.setActivityPictureList(activityPictureDao.getActivityPictureByActivityId(activity.getActivityId()));
                activityDTO.setActivitySignFileModelList(activitySignFileModelDao.getActivitySignFileModelByActivityId(activity.getActivityId()));
                activityDTOList.add(activityDTO);
            }
            response.setSuc(activityDTOList);
        }
        return response;
    }

    @Override
    public Response<Boolean> deleteActivityByActivityId(long activityId){
        Response<Boolean> response=new Response<>();

        Activity activityQueryResult = activityDao.getActivityByActivityId(activityId);
        if (activityQueryResult == null) {
            response.setFail(ResponseEnum.FAIL);
        }
        else {
            boolean result=activityDao.deleteActivityByActivityId(activityId) > 0;
            if(!result){
                response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            }
            else {
                response.setSuc(true);
            }
        }
        return response;
    }

    @Override
    public Response<List<ActivitySignFileModel>> getSignFileModel(long activityId) {
        Response<List<ActivitySignFileModel>> response=new Response<>();
        List<ActivitySignFileModel> activitySignFileModelList;

        activitySignFileModelList = activitySignFileModelDao.getActivitySignFileModelByActivityId(activityId);
        if(CollectionUtils.isEmpty(activitySignFileModelList)){
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            return response;
        }
        else{
            response.setSuc(activitySignFileModelList);
        }
        return response;
    }

    private boolean addActivitySignFileModel(long activityId, MultipartFile[] signFileModel){
        boolean result;
        String bucketName = "sign-file-models";
        String filename = "activity_"+activityId+"/";
        for(MultipartFile file : signFileModel) {
            String url = ossUtil.uploadFile(bucketName, file, filename+file.getOriginalFilename());
            if (StringUtils.isBlank(url)) {
                logger.error("[addActivitySignFileModel Fail], file: {}", SerialUtil.toJsonStr(file.getOriginalFilename()));
                return false;
            }
            ActivitySignFileModel activitySignFileModel = new ActivitySignFileModel();
            activitySignFileModel.setActivityId(activityId);
            activitySignFileModel.setFileModelName(file.getOriginalFilename());
            activitySignFileModel.setFileModelUrl(url);
            result=activitySignFileModelDao.addActivitySignFileModel(activitySignFileModel) > 0;
            if(!result){
                logger.error("[addActivitySignFileModel Fail], signFileModel: {}", SerialUtil.toJsonStr(signFileModel));
                return false;
            }
        }

        result = activityDao.updateIsActivitySignFileModelByActivityId(activityId,true) > 0;
        if(!result){
            logger.error("[updateIsActivitySignFileModelByActivityId Fail], activityId: {}", activityId);
            return false;
        }
        return true;
    }

    public boolean addActivityPicture(long activityId, MultipartFile[] activityPicture) {
        boolean result;
        String bucketName = "activity-picture-file-model";
        String filename = "activity_picture"+activityId+"/";
        for(MultipartFile file : activityPicture) {
            String url = ossUtil.uploadFile(bucketName, file, filename+file.getOriginalFilename());
            if (StringUtils.isBlank(url)) {
                logger.error("[addActivitySignFileModel Fail], file: {}", SerialUtil.toJsonStr(file.getOriginalFilename()));
                return false;
            }
            ActivityPicture Pic = new ActivityPicture();
            Pic.setActivityId(activityId);
            //Pic.setf(file.getOriginalFilename());
            Pic.setPictureUrl(url);
            result= activityPictureDao.addActivityPicture(Pic) > 0;
            if(!result){
                logger.error("[addActivityPicture Fail], Pic: {}", SerialUtil.toJsonStr(Pic));
                return result;
            }
        }
        result = activityDao.updateIsActivityPictureByActivityId(activityId,true) > 0;
        if(!result){
            logger.error("[updateIsActivityPictureByActivityId Fail], activityId: {}", activityId);
            return result;
        }
        return true;
    }

    @Override
    public Response<List<ActivityDTO>> getActivityByNumber(long number){
        Response<List<ActivityDTO>> response=new Response<>();
        List<Activity> activityList = activityDao.findActivityByNumber(number);
        List<ActivityDTO> activityDTOList = new ArrayList<>();
        if (activityList.size() == 0) {
            response.setFail(ResponseEnum.SWIPER_NEWS_NOT_FOUND);
            return response;
        }
        for(Activity activity:activityList){
           ActivityDTO activityDTO = new ActivityDTO();

           activityDTO.setActivityContent(activity.getActivityContent());
           activityDTO.setActivityId(activity.getActivityId());
           activityDTO.setActivityDate(activity.getActivityDate());
           activityDTO.setActivityName(activity.getActivityName());
           activityDTO.setActivityPlace(activity.getActivityPlace());
           activityDTO.setActivityType(activity.getActivityType());
           activityDTO.setEnrolledNumber(activity.getEnrolledNumber());
           activityDTO.setRequestedNumber(activity.getRequestedNumber());
           activityDTO.setActivityOrganizer(activity.getActivityOrganizer());

           activityDTO.setActivityPictureList(activityPictureDao.getActivityPictureByActivityId(activity.getActivityId()));


           activityDTOList.add(activityDTO);
        }
        if (activityDTOList.size() == 0) {
            response.setFail(ResponseEnum.SWIPER_NEWS_NOT_FOUND);
        }
        else {
            response.setSuc(activityDTOList);
        }
        return response;
    }

    private void convertActivity2ActivityDTO(Activity activity, ActivityDTO activityDTO){
        activityDTO.setActivityId(activity.getActivityId());
        activityDTO.setActivityDate(activity.getActivityDate());
        activityDTO.setActivityContent(activity.getActivityContent());
        activityDTO.setActivityName(activity.getActivityName());
        activityDTO.setActivityType(activity.getActivityType());
        activityDTO.setActivityPlace(activity.getActivityPlace());
        activityDTO.setActivityOrganizer(activity.getActivityOrganizer());
        activityDTO.setEnrolledNumber(activity.getEnrolledNumber());
        activityDTO.setRequestedNumber(activity.getRequestedNumber());
    }
}
