package com.example.biologyModel.Dao;

import com.example.biologyModel.Entity.Swiper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SwiperDao {
    @Insert("INSERT INTO swiper(news_id, swiper_picture, swiper_priority, swiper_text) " +
            "VALUES(#{newsId}, #{swiperPicture}, #{swiperPriority}, #{swiperText})")
    int addSwiper(Swiper swiper);

    @Update("UPDATE swiper SET swiper_priority = #{swiperPriority} WHERE swiper_id = #{swiperId}")
    int updateSwiperPriority(@Param("swiperPriority")String swiperPriority, @Param("swiperId")long swiperId);

    @Update("UPDATE swiper SET swiper_text = #{swiperText} WHERE swiper_id = #{swiperId}")
    int updateSwiperText(@Param("swiperText")String swiperText, @Param("swiperId")long swiperId);

    @ResultType(Swiper.class)
    @Select("SELECT swiper_id as swiperId, news_id as newsId, swiper_picture as swiperPicture, " +
            "swiper_priority as swiperPriority, swiper_text as swiperText FROM swiper WHERE news_id = #{newsId}")
    List<Swiper> findSwiperByNews(@Param("newsId")long newsId);

    @ResultType(Swiper.class)
    @Select("SELECT swiper_id as swiperId, news_id as newsId, swiper_picture as swiperPicture, " +
            "swiper_priority as swiperPriority, swiper_text as swiperText FROM swiper WHERE " +
            "swiper_priority = #{swiperPriority}")
    List<Swiper> findSwiperByPriority(@Param("swiperPriority")String swiperPriority);

    @Delete("DELETE FROM swiper WHERE swiper_id = #{swiperId}")
    int deleteSwiperById(@Param("swiperId")long swiperId);


    @ResultType(Swiper.class)
    @Select("SELECT swiper_id as swiperId, news_id as newsId, swiper_picture as swiperPicture, " +
            "swiper_priority as swiperPriority, swiper_text as swiperText FROM swiper " +
            "ORDER BY Rand(swiper_id) LIMIT #{number}")
    List<Swiper> findSwiperByNumber(@Param("number")long number);


}
