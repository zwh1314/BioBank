package com.example.volunteer.Dao;

import com.example.volunteer.DTO.ActivityDTO;
import com.example.volunteer.Entity.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public  interface ActivityDao {
    @Insert("INSERT INTO activity(activity_name,activity_content,activity_organizer,activity_type,activity_date,is_signfile_model,enrolled_number,requested_number,activity_place) VALUES (#{activityName},#{activityContent},#{activityOrganizer},#{activityType},NOW(),#{isSignFileModel},#{enrolledNumber},#{requestedNumber},#{activity_place});")
    int insertActivity(Activity activity);

    @ResultType(ActivityDTO.class)
    @Select("SELECT activity_id as activityId,activity_name as activityName,activity_content as activityContent,activity_place as activityPlace, " +
            "activity_organizer as activityOrganizer,activity_date as activityDate, is_signfile_model as isSignFileModel,activity_type as activityType, enrolled_number as enrolledNumber, requested_number as requestedNumber " +
            "FROM activity natural join activity_picture WHERE activity_id = #{activityId}")
    ActivityDTO getActivityByActivityId(@Param("activityId") long activityId);

    @ResultType(ActivityDTO.class)
    @Select("SELECT activity_id as activityId,activity_name as activityName,activity_content as activityContent,activity_place as activityPlace, " +
            "activity_organizer as activityOrganizer,activity_date as activityDate, is_signfile_model as isSignFileModel,activity_type as activityType,enrolled_number as enrolledNumber,requested_number as requestedNumber " +
            "FROM activity natural join activity_picture WHERE activity_name = #{activityName}")
    ActivityDTO getActivityByActivityName(@Param("activityName") String activityName);

    @ResultType(ActivityDTO.class)
    @Select("SELECT activity_id as activityId,activity_name as activityName,activity_content as activityContent," +
            "activity_organizer as activityOrganizer,activity_date as activityDate, is_signfile_model as isSignFileModel,activity_type as activityType,enrolled_number as enrolledNumber,requested_number as requestedNumber,activity_place as activityPlace " +
            "FROM activity natural join activity_picture WHERE activity_organizer = #{activityOrganizer}")
    List<ActivityDTO> getActivityByOrganizer(@Param("activityOrganizer") long activityOrganizer);

    @Update("UPDATE activity SET activity_name = #{activityName},activity_organizer = #{activityOrganizer}, activity_content = #{activityContent},activity_date = #{activityDate},activity_type = #{activityType},enrolled_number =#{enrolledNumber},request_number = #{requestNumber} WHERE activity_id = #{activityId}")
    int updateActivity(Activity activity);

    @Update("UPDATE activity SET is_signfile_model = #{isSignFileModel} WHERE activity_id = #{activityId}")
    int updateIsActivitySignFileModelByActivityId(@Param("activityId") long activityId,@Param("isSignFileModel") boolean isSignFileModel);

    @Delete("Delete From activity WHERE activity_id=#{activityId}")
    int deleteActivityByActivityId(@Param("activityId")long activityId);
}
