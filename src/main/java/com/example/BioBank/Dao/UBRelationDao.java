package com.example.BioBank.Dao;

import com.example.BioBank.DTO.UserBankRelationDTO;
import com.example.BioBank.Entity.UserBankRelation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UBRelationDao {
    @Insert("INSERT INTO user_bank_relation(user_id,bank_id,ub_priority) VALUES (#{userId},#{bankId},#{ubPriority});")
    void insertUserBankRelation(UserBankRelation userBankRelation);

    @ResultType(UserBankRelationDTO.class)
    @Select("SELECT user_id as userId, bank_id as bankId, ub_priority as ubPriority FROM user_bank_relation WHERE user_id = #{userId}")
    List<UserBankRelationDTO> getUserModelRelationByUserId(@Param("userId") long userId);

    @ResultType(UserBankRelationDTO.class)
    @Select("SELECT user_id as userId, bank_id as bankId, ub_priority as ubPriority FROM user_bank_relation WHERE bank_id = #{bankId}")
    List<UserBankRelationDTO> getUserBankRelationByBankId(@Param("bankId") long bankId);

    @ResultType(UserBankRelationDTO.class)
    @Select("SELECT user_id as userId, bank_id as bankId, ub_priority as ubPriority FROM user_bank_relation WHERE user_id = #{userId} and bank_id = #{bankId}")
    UserBankRelationDTO getUserBankRelationByUserIdAndBankId(@Param("userId") long userId, @Param("bankId") long bankId);

    @ResultType(UserBankRelationDTO.class)
    @Select("SELECT user_id as userId, bank_id as bankId, ub_priority as ubPriority FROM user_bank_relation WHERE bank_id = #{bankId} and ub_priority = 7")
    UserBankRelationDTO getBankOwner(@Param("bankId") long bankId);

    @Update("UPDATE user_bank_relation SET ub_priority = #{newUBPriority} WHERE user_id = #{userid} AND bank_id = #{bankId}")
    void updateUBPriority(@Param("userid") long userid, @Param("bankId") long bankId,@Param("newUBPriority") int newUBPriority);
}
