package com.example.BioBank.Dao;

import com.example.BioBank.DTO.BioModelDTO;
import com.example.BioBank.DTO.BioModelInfoDTO;
import com.example.BioBank.Entity.BioModel;
import com.example.BioBank.Entity.BioModelSQLConditions;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BioModelDao {
    @Insert("INSERT INTO bio_model(bank_id,model_tag,model_accessible,model_name,model_description,model_position," +
            "model_in_time,model_modify_time,model_temperature_most,model_temperature_least) VALUES " +
            "(#{bankId},#{modelTag},#{modelAccessible},#{modelName},#{modelDescription},#{modelPosition},NOW(),NOW()," +
            "#{modelTemperatureMost},#{modelTemperatureLeast});")
    @Options(useGeneratedKeys = true, keyProperty = "modelId", keyColumn = "model_id")
    void insertBioModelInfo(BioModel bioModel);

    @ResultType(BioModelInfoDTO.class)
    @Select("SELECT model_id as modelId, bank_id as bankId, model_tag as modelTag, model_accessible as modelAccessible, " +
            "model_name as modelName, model_description as modelDescription, model_position as modelPosition, " +
            "model_in_time as modelInTime, model_modify_time as modelModifyTime, model_temperature_most as modelTemperatureMost, " +
            "model_temperature_least as modelTemperatureLeast FROM bio_model WHERE model_id = #{modelId})")
    BioModelInfoDTO findBioModelInfoByModelId(@Param("modelId") long modelId);

    @ResultType(BioModelDTO.class)
    @Select("<script> SELECT model_id as modelId, bank_id as bankId, model_accessible as modelAccessible, " +
            "model_name as modelName FROM bio_model WHERE 1=1 " +
            "<if test='bankId > 0'>AND bank_id = #{bankId} </if>" +
            "<if test='modelAccessible == 1'>AND model_accessible = 1 </if>" +
            "<if test='modelAccessible == -1'>AND model_accessible = -1 </if>" +
            "<if test='modelTag != null'>AND model_tag LIKE CONCAT('%',#{modelTag},'%') </if>" +
            "<if test='modelName != null'>AND model_name LIKE CONCAT('%',#{modelName},'%') </if>" +
            "<if test='modelPosition != null'>AND model_position LIKE CONCAT('%',#{modelPosition},'%') </if>" +
            "</script>")
    List<BioModelDTO> findBioModelByConditions(BioModelSQLConditions bioModelSQLConditions);

    @ResultType(BioModelDTO.class)
    @Select("SELECT model_id as modelId, bank_id as bankId, model_accessible as modelAccessible, " +
            "model_name as modelName FROM bio_model WHERE TO_DAYS(NOW()) - TO_DAYS(model_in_time) <= 7")
    List<BioModelDTO> findBioModelInsertInOneWeek();

    @ResultType(BioModelDTO.class)
    @Select("SELECT model_id as modelId, bank_id as bankId, model_accessible as modelAccessible, " +
            "model_name as modelName FROM bio_model WHERE TO_DAYS(NOW()) - TO_DAYS(model_modify_time) <= 7")
    List<BioModelDTO> findBioModelModifiedInOneWeek();

    @Select("SELECT COUNT(model_id) FROM bio_model WHERE bank_id = #{bankId}")
    int findBioModelNumInTheBank(@Param("bankId") long bankId);

    @ResultType(BioModelDTO.class)
    @Select("SELECT model_id as modelId, bank_id as bankId, model_accessible as modelAccessible, " +
            "model_name as modelName FROM bio_model WHERE model_temperature_most <= #{modelTemperatureMost} AND " +
            "model_temperature_least >= #{modelTemperatureLeast}")
    List<BioModelDTO> findBioModelByTemperatureLimit(@Param("modelTemperatureMost") float modelTemperatureMost, @Param("modelTemperatureLeast") float modelTemperatureLeast);

    @Update("<script> UPDATE bio_model SET model_modify_time = NOW()" +
            "<if test='modelAccessible != 0'>, model_accessible = 0 - modelAccessible</if>" +
            "<if test='modelTag != null'>, model_tag = modelTag</if>" +
            "<if test='modelName != null'>, model_name = modelName</if>" +
            "<if test='modelPosition != null'>, model_position = modelPosition</if>" +
            " WHERE model_id = #{modelId} </script>")
    void updateConditions(BioModelSQLConditions bioModelSQLConditions);

    @Update("UPDATE bio_model SET model_modify_time = NOW()" +
            ", model_temperature_most = #{modelTemperatureMost}" +
            ", model_temperature_least = #{modelTemperatureLeast}" +
            " WHERE model_id = #{modelId}")
    void updateModelTemperatureLimit(@Param("modelId") long modelId, @Param("modelTemperatureMost") float modelTemperatureMost, @Param("modelTemperatureLeast") float modelTemperatureLeast);

    @Delete("Delete From bio_model WHERE model_id=#{modelId}")
    void deleteBioModelByModelId(@Param("modelId")long modelId);
}
