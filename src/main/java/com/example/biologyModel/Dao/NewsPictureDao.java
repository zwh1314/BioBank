package com.example.biologyModel.Dao;

import com.example.biologyModel.Entity.NewsPicture;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsPictureDao {
    @Insert("INSERT INTO news_picture(news_id, picture_url) VALUES(#{newsId}, #{newsPicture})")
    int addActivityNewsPicture(@Param("newsPicture")String newsPicture, @Param("newsId")long newsId);

    @ResultType(NewsPicture.class)
    @Select("SELECT picture_id as pictureId, news_id as newsId, picture_url as pictureUrl FROM news_picture WHERE news_id = #{newsId}")
    List<NewsPicture> findNewsPictureByNewsId(@Param("newsId")long newsId);

}
