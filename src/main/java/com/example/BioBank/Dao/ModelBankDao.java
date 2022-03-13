package com.example.BioBank.Dao;

import com.example.BioBank.DTO.ModelBankDTO;
import com.example.BioBank.Entity.ModelBank;
import com.example.BioBank.Entity.ModelBankSQLConditions;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public  interface ModelBankDao {
    @Insert("INSERT INTO model_bank(bank_capacity,bank_name,bank_accessible,bank_modify_time) VALUES " +
            "(#{bankCapacity},#{bankName},#{bankAccessible},NOW());")
    @Options(useGeneratedKeys = true, keyProperty = "bankId", keyColumn = "bank_id")
    void insertModelBank(ModelBank modelBank);

    @ResultType(ModelBankDTO.class)
    @Select("<script> SELECT bank_id as bankId,bank_capacity as bankCapacity,bank_name as bankName,bank_accessible as bankAccessible, " +
            "bank_modify_time as bankModifyTime FROM model_bank WHERE 1=1 "+
            "<if test='bankId > 0'>AND bank_id = #{bankId} </if>" +
            "<if test='bankCapacity > 0'>AND bank_capacity >= #{bankCapacity} </if>" +
            "<if test='bankAccessible == 1'>AND bank_accessible = 1 </if>" +
            "<if test='bankAccessible == -1'>AND bank_accessible = -1 </if>" +
            "<if test='bankName != null'>AND bank_name LIKE CONCAT('%',#{bankName},'%') </if>" +
            "</script>")
    List<ModelBankDTO> getModelBankByConditions(ModelBankSQLConditions modelBankSQLConditions);

    @ResultType(ModelBankDTO.class)
    @Select("SELECT bank_id as bankId,bank_capacity as bankCapacity,bank_name as bankName,bank_accessible as bankAccessible, " +
            "bank_modify_time as bankModifyTime FROM model_bank WHERE TO_DAYS(NOW()) - TO_DAYS(bank_modify_time) <= 7")
    List<ModelBankDTO> findModelBankModifiedInOneWeek();

    @Update("<script> UPDATE model_bank SET bank_modify_time = NOW()" +
            "<if test='bankName != null'>, bank_name = bankName</if>" +
            "<if test='bankCapacity > 0'>, bank_capacity = bankCapacity</if>" +
            "<if test='bankAccessible != 0'>, bank_accessible = 0 - bankAccessible</if>" +
            " WHERE bank_id = #{bankId} </script>")
    void updateConditions(ModelBankSQLConditions modelBankSQLConditions);

    @Delete("Delete From model_bank WHERE bank_id=#{bankId}")
    void deleteModelBankByBankId(@Param("bankId")long bankId);
}
