package com.example.BioBank.Dao;

import com.example.BioBank.DTO.UserModelRelationDTO;
import com.example.BioBank.Entity.UserModelRelation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UMRelationDao {
    @Insert("INSERT INTO user_model_relation(user_id,model_id,um_priority) VALUES (#{userId},#{modelId},#{umPriority});")
    void insertUserModelRelation(UserModelRelation userModelRelation);

    @ResultType(UserModelRelationDTO.class)
    @Select("SELECT user_id as userId, model_id as modelId, um_priority as umPriority FROM user_model_relation WHERE user_id = #{userId}")
    List<UserModelRelationDTO> getUserModelRelationByUserId(@Param("userId") long userId);

    @ResultType(UserModelRelationDTO.class)
    @Select("SELECT user_id as userId, model_id as modelId, um_priority as umPriority FROM user_model_relation WHERE model_id = #{modelId}")
    List<UserModelRelationDTO> getUserModelRelationByModelId(@Param("modelId") long modelId);

    @ResultType(UserModelRelationDTO.class)
    @Select("SELECT user_id as userId, model_id as modelId, um_priority as umPriority FROM user_model_relation WHERE user_id = #{userId} and model_id = #{modelId}")
    UserModelRelationDTO getUserModelRelationByUserIdAndModelId(@Param("userId") long userId, @Param("modelId") long modelId);

    @Update("UPDATE user_model_relation SET um_priority = #{newUMPriority} WHERE userId = #{userid} AND model_id = #{modelId}")
    void updateUMPriority(@Param("userid") long userid, @Param("modelId") long modelId, @Param("newUMPriority") int newUMPriority);
}
