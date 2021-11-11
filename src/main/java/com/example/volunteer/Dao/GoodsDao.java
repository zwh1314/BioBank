package com.example.volunteer.Dao;

import com.example.volunteer.DTO.GoodsDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsDao {
    @ResultType(GoodsDTO.class)
    @Select("SELECT goods_id as goodsId, intro as intro,value as value,num as num,name as name,goods_picture_url as goodsPictureUrl,goods_picture_intro_url as goodsPictureIntroUrl FROM goods ")
    List<GoodsDTO> findAllGoods();


    @Update("UPDATE goods SET num = #{num} WHERE goods_id = #{goodsId}")
    int updateNum(@Param("num") long num, @Param("goodsId") long goodsId);

    @Select("SELECT num  FROM goods WHERE goods_id = #{goodsId}")
    long getGoodsNumByGoodsId(@Param("goodsId")long goodsId);

    @ResultType(GoodsDTO.class)
    @Select("SELECT goods_id as goodsId, intro as intro,value as value,num as num,name as name,goods_picture_url as goodsPictureUrl,goods_picture_intro_url as goodsPictureIntroUrl FROM goods WHERE goods_id = #{goodsId}")
    GoodsDTO getGoodsInfoByGoodsId(@Param("goodsId")long goodsId);

}
