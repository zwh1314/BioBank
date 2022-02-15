package com.example.BioBank.Dao;

import com.example.BioBank.DTO.ActivityDTO;
import com.example.BioBank.Entity.ActivityUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityUserDao {
    @Insert("INSERT INTO activity_user(activity_id,user_id,form_status,form_date,is_focus) VALUES (#{activityId},#{userId},#{formStatus},#{formDate},#{isFocus});")
    int addActivityUser(ActivityUser activityUser);


    @Update("UPDATE activity_user SET form_status = #{formStatus} WHERE activity_id = #{activityId} AND user_id = #{userId}")
    int updateFormStatusByUserIdAndActivityId(@Param("activityId") long activityId, @Param("userId")long userId, @Param("formStatus")boolean formStatus);

    @Update("UPDATE activity_user SET form_date = NOW() WHERE activity_id = #{activityId} AND user_id = #{userId}")
    int updateFormDateByUserIdAndActivityId(@Param("activityId") long activityId, @Param("userId")long userId);

    @ResultType(ActivityUser.class)
    @Select("SELECT user_id as userId, activity_id as activityId, form_date as formDate, form_status as formStatus FROM activity_user WHERE user_id = #{userId}")
    List<ActivityUser> findActivityUserByUserId(@Param("userId")long userId);

    @ResultType(ActivityUser.class)
    @Select("SELECT user_id as userId, activity_id as activityId, form_date as formDate, form_status as formStatus FROM activity_user WHERE user_id = #{userId} AND activity_id = #{activityId}")
    ActivityUser findActivityUserByUserIdAndActivityId(@Param("activityId")long activityId, @Param("userId")long userId);

    @ResultType(ActivityUser.class)
    @Select("SELECT user_id as userId, activity_id as activityId, form_date as formDate, form_status as formStatus FROM activity_user WHERE activity_id = #{activityId}")
    List<ActivityUser> findActivityUserByActivityId(@Param("activityId")long activityId);

    @Delete("DELETE FROM activity_user WHERE user_id = #{userId}")
    int deleteActivityUserByUserId(@Param("userId")long userId);

    @Delete("DELETE FROM activity_user WHERE activity_id = #{activityId}")
    int deleteActivityUserByActivityId(@Param("activityId")long activityId);

    @ResultType(ActivityDTO.class)
    @Select("SELECT activity_id as activityId,activity_name as activityName,activity_content as activityContent," +
            "activity_organizer as activityOrganizer,activity_date as activityDate, is_signfile_model as isSignFileModel,activity_type as ActivityType,enrolled_number as enrolledNumber,requested_number as requestedNumber, activity_place as activityPlace " +
            "FROM activity WHERE activity_id in (SELECT activity_id as activityId FROM activity_user WHERE user_id = #{userId} AND form_status = 1 )")
    List<ActivityDTO> findSignedUpActivityByUserId(@Param("userId")long userId);

    @ResultType(ActivityDTO.class)
    @Select("SELECT activity_id as activityId,activity_name as activityName,activity_content as activityContent, " +
            " activity_organizer as activityOrganizer,activity_date as activityDate, is_signfile_model as isSignFileModel,activity_type as activityType,enrolled_number as enrolledNumber,requested_number as requestedNumber, " +
            " picture_id as pictureId, picture_url as pictureUrl, activity_place as activityPlace" +
            " FROM activity natural join activity_picture WHERE activity_date < NOW() AND activity_id in (SELECT activity_id  FROM activity_user WHERE form_status = 1 AND user_id = #{userId} " +
            ")")
    List<ActivityDTO> findParticipatedActivityByUserId(long userId);

    @ResultType(ActivityDTO.class)
    @Select("SELECT activity_id as activityId ,activity_name  as activityName,activity_content as activityContent, " +
            "activity_organizer as activityOrganizer ,enrolled_number as enrolledNumber, requested_number as requestedNumber," +
            " activity_type as activityType ,activity_date as activityDate, activity_place as activityPlace FROM activity" +
            " WHERE activity_id in (SELECT activity_id FROM activity_user WHERE user_id = #{userId} AND is_focus = 1 )")
    List<ActivityDTO> getFocusedByUserId(long userId);

    @Update("UPDATE activity_user SET is_focus = #{isFocus} WHERE user_id = #{userId} AND activity_id = #{activityId}")
    int updateIsFocus(@Param("activityId")long activityId, @Param("userId")long userId, @Param("isFocus")boolean isFocus);

}