package com.example.biologyModel.Service;

import com.example.biologyModel.DTO.ActivityNewsDTO;
import com.example.biologyModel.Entity.ActivityNews;
import com.example.biologyModel.Response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActivityNewsService {
    Response<Boolean> addActivityNews(long userId, ActivityNews activityNews);

    Response<Boolean> updateActivityNewsContent(MultipartFile activityNewsContent, long newsId);

    Response<Boolean> updateActivityNewsPicture(MultipartFile activityNewsPicture, long newsId);

    Response<Boolean> updateActivityNewsTitle(String activityNewsTitle, long newsId);

    Response<List<ActivityNewsDTO>> getActivityNewsByActivityId(long activityId);

    Response<List<ActivityNewsDTO>> getActivityNewsByPublisherId(long publisherId);

    Response<List<ActivityNewsDTO>> getActivityNewsInOneWeek();

    Response<List<ActivityNewsDTO>> getActivityNewsByRelativeText(String relativeText);

    Response<Boolean> deleteActivityNewsById(long activityNewsId);

    Response<List<ActivityNewsDTO>> getActivityNewsByNumber(long number);
}
