package com.example.volunteer.Dao;

import com.example.volunteer.Entity.GoodsSwiper;
import com.example.volunteer.Entity.Swiper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsSwiperDao {
    @Insert("INSERT INTO goods_swiper(news_id, swiper_picture, swiper_priority, swiper_text) " +
            "VALUES(#{newsId}, #{swiperPicture}, #{swiperPriority}, #{swiperText})")
    int addSwiper(GoodsSwiper swiper);

    @Update("UPDATE goods_swiper SET swiper_priority = #{swiperPriority} WHERE swiper_id = #{swiperId}")
    int updateSwiperPriority(@Param("swiperPriority")String swiperPriority, @Param("swiperId")long swiperId);

    @Update("UPDATE goods_swiper SET swiper_text = #{swiperText} WHERE swiper_id = #{swiperId}")
    int updateSwiperText(@Param("swiperText")String swiperText, @Param("swiperId")long swiperId);

    @ResultType(Swiper.class)
    @Select("SELECT swiper_id as swiperId, news_id as newsId, swiper_picture as swiperPicture, " +
            "swiper_priority as swiperPriority, swiper_text as swiperText FROM goods_swiper WHERE news_id = #{newsId}")
    List<GoodsSwiper> findSwiperByNews(@Param("newsId")long newsId);

    @ResultType(GoodsSwiper.class)
    @Select("SELECT swiper_id as swiperId, news_id as newsId, swiper_picture as swiperPicture, " +
            "swiper_priority as swiperPriority, swiper_text as swiperText FROM goods_swiper WHERE " +
            "swiper_priority = #{swiperPriority}")
    List<GoodsSwiper> findSwiperByPriority(@Param("swiperPriority")String swiperPriority);

    @Delete("DELETE FROM goods_swiper WHERE swiper_id = #{swiperId}")
    int deleteSwiperById(@Param("swiperId")long swiperId);


    @ResultType(GoodsSwiper.class)
    @Select("SELECT swiper_id as swiperId, news_id as newsId, swiper_picture as swiperPicture, " +
            "swiper_priority as swiperPriority, swiper_text as swiperText FROM goods_swiper " +
            "ORDER BY Rand(swiper_id) LIMIT #{number}")
    List<GoodsSwiper> findSwiperByNumber(@Param("number")long number);


}
