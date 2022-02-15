package com.example.BioBank.Dao;

import com.example.BioBank.Entity.ActivityNews;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityNewsDao {
    @Insert("INSERT INTO activity_news(activity_id, news_content, news_title, " +
            "news_publisher, news_date) VALUES(#{activityId}, #{newsContent}, " +
            "#{newsTitle}, #{newsPublisher}, NOW())")
    int addActivityNews(ActivityNews activityNews);

    @Update("UPDATE activity_news SET news_content = #{newsContent} WHERE news_id = #{newsId}")
    int updateActivityNewsContent(@Param("newsContent")String newsContent, @Param("newsId")long newsId);

    @Update("UPDATE activity_news SET news_title = #{newsTitle} WHERE news_id = #{newsId}")
    int updateActivityNewsTitle(@Param("newsTitle")String newsTitle, @Param("newsId")long newsId);

    @ResultType(ActivityNews.class)
    @Select("SELECT news_id as newsId, activity_id as activityId, news_content as newsContent, " +
            "news_title as newsTitle, news_publisher as newsPublisher, " +
            "news_date as newsDate FROM activity_news WHERE activity_id = #{activityId}")
    List<ActivityNews> findActivityNewsById(@Param("activityId")long activityId);

    @ResultType(ActivityNews.class)
    @Select("SELECT news_id as newsId, activity_id as activityId, news_content as newsContent, " +
            "news_title as newsTitle, news_publisher as newsPublisher, " +
            "news_date as newsDate FROM activity_news WHERE TO_DAYS(NOW()) - TO_DAYS(news_date) <= 7")
    List<ActivityNews> findActivityNewsInOneWeek();

    @ResultType(ActivityNews.class)
    @Select("SELECT news_id as newsId, activity_id as activityId, news_content as newsContent, " +
            "news_title as newsTitle, news_publisher as newsPublisher, " +
            "news_date as newsDate FROM activity_news WHERE news_publisher = #{newsPublisher}")
    List<ActivityNews> findActivityNewsByPublisher(@Param("newsPublisher")long newsPublisher);

    @ResultType(ActivityNews.class)
    @Select("SELECT news_id as newsId, activity_id as activityId, news_content as newsContent, " +
            "news_title as newsTitle, news_publisher as newsPublisher, " +
            "news_date as newsDate FROM activity_news WHERE news_content LIKE CONCAT('%',#{textInclude},'%')")
    List<ActivityNews> findActivityNewsByContent(@Param("textInclude")String textInclude);

    @Delete("DELETE FROM activity_news WHERE news_id = #{newsId}")
    int deleteActivityNewsById(@Param("newsId")long newsId);

    @ResultType(ActivityNews.class)
    @Select("SELECT news_id as newsId, activity_id as activityId, news_content as newsContent, " +
            " news_title as newsTitle, news_publisher as newsPublisher, " +
            "news_date as newsDate FROM activity_news " +
            "ORDER BY Rand(news_id) LIMIT #{number}")
    List<ActivityNews> findActivityNewsByNumber(@Param("number")long number);

}
