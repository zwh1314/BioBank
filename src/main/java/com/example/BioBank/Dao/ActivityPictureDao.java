package com.example.BioBank.Dao;

import com.example.BioBank.Entity.ActivityPicture;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityPictureDao {
    @Insert("INSERT INTO activity_picture (activity_id, picture_url) VALUES (#{activityId}, #{pictureUrl})")
    int addActivityPicture(ActivityPicture activityPicture);

    @ResultType(ActivityPicture.class)
    @Select("SELECT picture_id as pictureId ,activity_id as activityId , picture_url as pictureUrl FROM activity_picture WHERE activity_id = #{activityId}")
    List<ActivityPicture> getActivityPictureByActivityId(@Param("activityId") long activityId);

    @Update("UPDATE activity_picture SET  picture_url = #{pictureUrl} WHERE activity_id = #{activityId}")
    int updateActivityPicture(ActivityPicture activityPicture);

    @Delete("Delete From activity_picture WHERE activity_id=#{activityId}")
    int deleteActivityPictureByActivityId(@Param("activityId")long activityId);
}
