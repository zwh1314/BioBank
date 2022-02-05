package com.example.biologyModel.Service.ServiceImpl;

import com.example.biologyModel.DTO.ActivityNewsDTO;
import com.example.biologyModel.Dao.ActivityNewsDao;
import com.example.biologyModel.Dao.NewsPictureDao;
import com.example.biologyModel.Entity.ActivityNews;
import com.example.biologyModel.Entity.NewsPicture;
import com.example.biologyModel.Response.Response;
import com.example.biologyModel.Service.ActivityNewsService;
import com.example.biologyModel.enums.ResponseEnum;
import com.example.biologyModel.utils.OSSUtil;
import com.example.biologyModel.utils.SerialUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityNewsServiceImpl implements ActivityNewsService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityNewsServiceImpl.class);

    @Autowired
    private ActivityNewsDao activityNewsDao;

    @Autowired
    private NewsPictureDao newsPictureDao;

    @Autowired
    private OSSUtil ossUtil;

    @Override
    public Response<Boolean> addActivityNews(long userId, ActivityNews activityNews){
        Response<Boolean> response=new Response<>();

        activityNews.setNewsPublisher(userId);
        boolean result = activityNewsDao.addActivityNews(activityNews) > 0;
        if (!result) {
            logger.error("[addActivityNews Fail], activityNews: {}", SerialUtil.toJsonStr(activityNews));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            return response;
        }
        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Boolean> updateActivityNewsContent(MultipartFile activityNewsContent, long newsId){
        Response<Boolean> response=new Response<>();
        String bucketName = "newscontent-url";
        String filename = "newsId_"+newsId+"/";
        String url = ossUtil.uploadFile(bucketName, activityNewsContent, filename + activityNewsContent.getOriginalFilename());
        if (StringUtils.isBlank(url)) {
            logger.error("[addVideo Fail], activityNewsPicture: {}", SerialUtil.toJsonStr(activityNewsContent.getOriginalFilename()));
            response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
            return response;
        }

        boolean result = activityNewsDao.updateActivityNewsContent(url,newsId) > 0;
        if (!result) {
            logger.error("[updateActivityNewsContent Fail], newsId: {}", SerialUtil.toJsonStr(newsId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            return response;
        }

        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Boolean> updateActivityNewsPicture(MultipartFile activityNewsPicture, long newsId){
        Response<Boolean> response=new Response<>();

        String bucketName = "newspicture-url";
        String filename = "newsId_"+newsId+"/";
        String url = ossUtil.uploadFile(bucketName, activityNewsPicture, filename + activityNewsPicture.getOriginalFilename());
        if (StringUtils.isBlank(url)) {
            logger.error("[addVideo Fail], activityNewsPicture: {}", SerialUtil.toJsonStr(activityNewsPicture.getOriginalFilename()));
            response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
            return response;
        }

        boolean result = newsPictureDao.addActivityNewsPicture(url, newsId) > 0;
        if (!result) {
            logger.error("[updateActivityNewsPicture Fail], newsId: {}", newsId);
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            return response;
        }

        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Boolean> updateActivityNewsTitle(String activityNewsTitle, long newsId){
        Response<Boolean> response=new Response<>();
        boolean result = activityNewsDao.updateActivityNewsTitle(activityNewsTitle,newsId) > 0;
        if (!result) {
            logger.error("[updateActivityNewsTitle Fail], newsId: {}", SerialUtil.toJsonStr(newsId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<List<ActivityNewsDTO>> getActivityNewsByActivityId(long activityId){
        Response<List<ActivityNewsDTO>> response=new Response<>();
        List<ActivityNewsDTO> activityNewsDTOList = new ArrayList<>();

        List<ActivityNews> activityNewsList = activityNewsDao.findActivityNewsById(activityId);
        if (activityNewsList.size() == 0) {
            response.setFail(ResponseEnum.ACTIVITY_NEWS_ACTIVITY_NOT_FOUND);
        }
        else{
            for(ActivityNews activityNews:activityNewsList) {
                List<NewsPicture> newsPictureList = newsPictureDao.findNewsPictureByNewsId(activityNews.getNewsId());
                ActivityNewsDTO activityNewsDTO = transferActivityNews2DTO(activityNews,newsPictureList);
                activityNewsDTOList.add(activityNewsDTO);
            }
            response.setSuc(activityNewsDTOList);
        }
        return response;
    }

    @Override
    public Response<List<ActivityNewsDTO>> getActivityNewsByPublisherId(long publisherId){
        Response<List<ActivityNewsDTO>> response=new Response<>();
        List<ActivityNewsDTO> activityNewsDTOList = new ArrayList<>();

        List<ActivityNews> activityNewsList = activityNewsDao.findActivityNewsByPublisher(publisherId);
        if (activityNewsList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_PUBLISHER_NOT_FOUND);
        }
        else{
            for(ActivityNews activityNews:activityNewsList) {
                List<NewsPicture> newsPictureList = newsPictureDao.findNewsPictureByNewsId(activityNews.getNewsId());
                ActivityNewsDTO activityNewsDTO = transferActivityNews2DTO(activityNews,newsPictureList);
                activityNewsDTOList.add(activityNewsDTO);
            }
            response.setSuc(activityNewsDTOList);
        }
        return response;
    }

    @Override
    public Response<List<ActivityNewsDTO>> getActivityNewsInOneWeek(){
        Response<List<ActivityNewsDTO>> response=new Response<>();
        List<ActivityNewsDTO> activityNewsDTOList = new ArrayList<>();

        List<ActivityNews> activityNewsList = activityNewsDao.findActivityNewsInOneWeek();
        if (activityNewsList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        }
        else{
            for(ActivityNews activityNews:activityNewsList) {
                List<NewsPicture> newsPictureList = newsPictureDao.findNewsPictureByNewsId(activityNews.getNewsId());
                ActivityNewsDTO activityNewsDTO = transferActivityNews2DTO(activityNews,newsPictureList);
                activityNewsDTOList.add(activityNewsDTO);
            }
            response.setSuc(activityNewsDTOList);
        }
        return response;
    }

    @Override
    public Response<List<ActivityNewsDTO>> getActivityNewsByRelativeText(String relativeText){
        Response<List<ActivityNewsDTO>> response=new Response<>();
        List<ActivityNewsDTO> activityNewsDTOList = new ArrayList<>();

        List<ActivityNews> activityNewsList = activityNewsDao.findActivityNewsByContent(relativeText);
        if (activityNewsList.size() == 0) {
            response.setFail(ResponseEnum.OBJECT_RELATIVE_TEXT_NOT_FOUND);
        }
        else{
            for(ActivityNews activityNews:activityNewsList) {
                List<NewsPicture> newsPictureList = newsPictureDao.findNewsPictureByNewsId(activityNews.getNewsId());
                ActivityNewsDTO activityNewsDTO = transferActivityNews2DTO(activityNews,newsPictureList);
                activityNewsDTOList.add(activityNewsDTO);
            }
            response.setSuc(activityNewsDTOList);
        }
        return response;
    }

    @Override
    public Response<Boolean> deleteActivityNewsById(long activityNewsId){
        Response<Boolean> response=new Response<>();

        boolean result=activityNewsDao.deleteActivityNewsById(activityNewsId) > 0;
        if(!result){
            logger.error("[deleteActivityNewsById Fail], activityNewsId: {}", SerialUtil.toJsonStr(activityNewsId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }
    @Override
    public Response<List<ActivityNewsDTO>> getActivityNewsByNumber(long number){
        Response<List<ActivityNewsDTO>> response=new Response<>();
        List<ActivityNewsDTO> activityNewsDTOList = new ArrayList<>();
        List<ActivityNews> activityNewsList = activityNewsDao.findActivityNewsByNumber(number);
        if (activityNewsList.size() == 0) {
            response.setFail(ResponseEnum.ACTIVITY_NEWS_ACTIVITY_NOT_FOUND);
        }
        for(ActivityNews activityNews: activityNewsList){

            activityNewsDTOList.add(transferActivityNews2DTO2(activityNews));
        }
        if (activityNewsDTOList.size() == 0) {
            response.setFail(ResponseEnum.ACTIVITY_NEWS_ACTIVITY_NOT_FOUND);
        }
        else{
            response.setSuc(activityNewsDTOList);
        }
        return response;
    }

    private ActivityNewsDTO transferActivityNews2DTO(ActivityNews activityNews,List<NewsPicture> newsPictureList){
        ActivityNewsDTO activityNewsDTO = new ActivityNewsDTO();
        activityNewsDTO.setNewsPictureList(newsPictureList);
        activityNewsDTO.setActivityId(activityNews.getActivityId());
        activityNewsDTO.setNewsId(activityNews.getNewsId());
        activityNewsDTO.setNewsDate(activityNews.getNewsDate());
        activityNewsDTO.setNewsContent(activityNews.getNewsContent());
        activityNewsDTO.setNewsTitle(activityNews.getNewsTitle());
        activityNewsDTO.setNewsPublisher(activityNews.getNewsPublisher());
        return activityNewsDTO;
    }
    private ActivityNewsDTO transferActivityNews2DTO2(ActivityNews activityNews){
        ActivityNewsDTO activityNewsDTO = new ActivityNewsDTO();
        activityNewsDTO.setActivityId(activityNews.getActivityId());
        activityNewsDTO.setNewsId(activityNews.getNewsId());
        activityNewsDTO.setNewsDate(activityNews.getNewsDate());
        activityNewsDTO.setNewsContent(activityNews.getNewsContent());
        activityNewsDTO.setNewsTitle(activityNews.getNewsTitle());
        activityNewsDTO.setNewsPublisher(activityNews.getNewsPublisher());
        activityNewsDTO.setNewsPictureList(newsPictureDao.findNewsPictureByNewsId(activityNews.getNewsId()));
        return activityNewsDTO;
    }
}
