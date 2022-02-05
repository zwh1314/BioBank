package com.example.biologyModel.Dao;

import com.example.biologyModel.Entity.ActivitySignFileModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivitySignFileModelDao {
    @Insert("INSERT INTO acitivty_signfile_model(activity_id,file_model_name,file_model_url) VALUES (#{activityId},#{fileModelName},#{fileModelUrl});")
    int addActivitySignFileModel(ActivitySignFileModel activitySignFileModel);

    @ResultType(ActivitySignFileModel.class)
    @Select("SELECT file_model_id as fileModelId, activity_id as activityId,file_model_name as fileModelName,file_model_url as fileModelUrl FROM acitivty_signfile_model WHERE activity_id = #{activityId}")
    List<ActivitySignFileModel> getActivitySignFileModelByActivityId(@Param("activityId") long activityId);

    @Update("UPDATE acitivty_signfile_model SET file_model_name = #{fileModelName},file_model_url = #{fileModelUrl} WHERE activity_id = #{activityId}")
    int updateActivitySignFileModel(ActivitySignFileModel activitySignFileModel);

    @Delete("Delete From acitivty_signfile_model WHERE activity_id=#{activityId}")
    int deleteAActivitySignFileModelByActivityId(@Param("activityId")long activityId);
}
