package com.example.volunteer.Dao;

import com.example.volunteer.DTO.ActivityDTO;
import com.example.volunteer.Entity.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public  interface ActivityDao {
    @Insert("INSERT INTO activity(activity_name,activity_content,activity_organizer,activity_date,activity_picture) VALUES (#{activityName},#{activityContent},#{activityOrganizer},#{activityDate});")
    int insertActivity(Activity activity);

    @ResultType(ActivityDTO.class)
    @Select("SELECT activity_id as activityId,activity_name as activityName,activity_content as activityContent,activity_organizer as activityOrganizer,activity_date as activityDate,activity_picture as activityPicture FROM activity WHERE activity_id = #{activityId}")
    ActivityDTO getActivityByActivityId(@Param("activityId") long activityId);

    @ResultType(ActivityDTO.class)
    @Select("SELECT activity_id as activityId,activity_name as activityName,activity_content as activityContent,activity_organizer as activityOrganizer,activity_date as activityDate,activity_picture as activityPicture FROM activity WHERE activity_name = #{activityName}")
    ActivityDTO getActivityByActivityName(@Param("activityName") String activityName);

    @ResultType(ActivityDTO.class)
    @Select("SELECT activity_id as activityId,activity_name as activityName,activity_content as activityContent,activity_organizer as activityOrganizer,activity_date as activityDate,activity_picture as activityPicture FROM activity WHERE activity_organizer = #{activityOrganizer}")
    ActivityDTO getActivityByOrganizer(@Param("activityOrganizer") String activityOrganizer);

    @Update("UPDATE activity SET activity_name = #{activityName},activity_organizer = #{activityOrganizer}, activity_content = #{activityContent},activity_date = #{activityDate},activity_picture = #{activityPicture} WHERE activity_id = #{activityId}")
    int updateActivity(Activity activity);

    @ResultType(Activity.class)
    @Select("SELECT activity_id as activityId,activity_name as activityName,activity_content as activityContent,activity_organizer as activityOrganizer,activity_date as activityDate,activity_picture as activityPicture FROM activity" +
            " WHERE activity_id >= (SELECT FLOOR( MAX(activity_id) * RAND()) FROM `activity` ) ORDER BY activity_id LIMIT #{number}")
    List<Activity> findActivityByNumber(@Param("number")long number);

    @Delete("Delete From activity WHERE activity_id=#{activityId}")
    int deleteActivityByActivityId(@Param("activityId")long activityId);
}
