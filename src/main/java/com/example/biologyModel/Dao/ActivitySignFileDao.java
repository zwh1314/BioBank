package com.example.biologyModel.Dao;

import com.example.biologyModel.Entity.ActivitySignFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivitySignFileDao {
    @Insert("INSERT INTO activity_signfile(activity_id,user_id,file_name,file_url) VALUES (#{activityId},#{userId},#{fileName},#{fileUrl});")
    int addActivitySignFile(ActivitySignFile activitySignFile);

    @ResultType(ActivitySignFile.class)
    @Select("SELECT activity_id as activityId,user_id as userId,file_name as fileName,file_url as fileUrl FROM activity_signfile WHERE activity_id = #{activityId}")
    List<ActivitySignFile> getActivitySignFileByActivityId(@Param("activityId") long activityId);

    @ResultType(ActivitySignFile.class)
    @Select("SELECT activity_id as activityId,user_id as userId,file_name as fileName,file_url as fileUrl FROM activity_signfile WHERE user_id = #{userId}")
    List<ActivitySignFile> getActivitySignFileByUserId(@Param("userId") long userId);

    @Update("UPDATE activity_signfile SET file_name = #{fileName},file_url = #{fileUrl} WHERE activity_id = #{activityId} AND user_id = #{userId}")
    int updateActivitySignFile(ActivitySignFile activitySignFile);

    @Delete("Delete From activity_signfile WHERE activity_id=#{activityId}")
    int deleteActivitySignFileByActivityId(@Param("activityId")long activityId);
}
