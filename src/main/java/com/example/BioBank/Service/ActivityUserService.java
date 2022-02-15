package com.example.BioBank.Service;

import com.example.BioBank.DTO.ActivityDTO;
import com.example.BioBank.Entity.ActivitySignFile;
import com.example.BioBank.Entity.ActivityUser;
import com.example.BioBank.Response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface  ActivityUserService {
    /**
     * 通过id查询活动
     * @param userId
     * @return 查询结果链表
     */
    Response<List<ActivityUser>> getActivityUserByUserId(long userId);

    /**
     * 通过id查询活动
     * @param activityId
     * @return 查询结果链表
     */
    Response<List<ActivityUser>> getActivityUserByActivityId(long activityId);


    /**删除活动
     * @param activityId
     * @return 成功返回true，失败返回false
     */
    Response<Boolean> deleteActivityUserByActivityId(long activityId);

    /**删除活动
     * @param userId
     * @return 成功返回true，失败返回false
     */
    Response<Boolean> deleteActivityUserByUserId(long userId);

    /**下载活动报名表
     * @param activityId
     * @return 查询结果链表
     */
    Response<List<ActivitySignFile>> getSignFile(long activityId);

    /**上传活动报名表
     * @param activityId
     * @param signFile
     * @return 成功返回true，失败返回false
     */
    Response<Boolean> uploadSignFile(long userId, long activityId, MultipartFile[] signFile);

    /**通过id查询已经参加的活动
     * @param userId
     * @return 查询结果链表
     */
    Response<List<ActivityDTO>>getParticipatedActivityByUserId(long userId);

    /**
     * 获得用户已关注的活动
     * @param userId
     * @return
     */
    Response<List<ActivityDTO>> getFocusedByUserId(long userId);
    /**
     * 获得用户已报名的活动
     * @param userId
     * @return
     */
    public Response<List<ActivityDTO>> getSignedUpActivityByUserId(long userId);

    Response<Boolean> updateActivityIsFocus(long activityId, long userId, boolean isFocus);
}
