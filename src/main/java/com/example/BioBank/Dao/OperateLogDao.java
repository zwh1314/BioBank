package com.example.BioBank.Dao;

import com.example.BioBank.DTO.OperateLogDTO;
import com.example.BioBank.Entity.ModelOperateLogSQLConditions;
import com.example.BioBank.Entity.OperateLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OperateLogDao {
    @Insert("INSERT INTO model_operate_log(user_id, bank_id, model_id, operate_time, operate_type) " +
            "VALUES (#{userId}, #{bankId}, #{modelId}, NOW() ,#{operateType)")
    void insertOperateLog(OperateLog operateLog);

    @ResultType(OperateLogDTO.class)
    @Select("SELECT operate_log_id as operateLogId, user_id as userId, bank_id as bankId, model_id as modelId, " +
            "operate_time as operateTime, operate_type as operateType FROM model_operate_log WHERE " +
            "TO_DAYS(NOW()) - TO_DAYS(operate_time) <= 7")
    List<OperateLogDTO> findModelOperateLogInOneWeek();

    @Select("<script> SELECT operate_log_id as operateLogId, user_id as userId, bank_id as bankId, model_id as modelId, " +
            "operate_time as operateTime, operate_type as operateType FROM model_operate_log WHERE 1=1 " +
            "<if test='userId > 0'>AND user_id = #{userId} </if>" +
            "<if test='bankId > 0'>AND bank_id = #{bankId} </if>" +
            "<if test='modelId > 0'>AND model_id = #{modelId} </if>" +
            "<if test='operateType > 0'>AND operate_type = #{operateType} </if>" +
            "</script>")
    List<OperateLogDTO> findModelOperateLogByConditions(ModelOperateLogSQLConditions modelOperateLogSQLConditions);

    @Delete("Delete From model_operate_log WHERE TO_DAYS(NOW()) - TO_DAYS(operate_time) > 31")
    void deleteModelOperateLogOverOneMonth();
}
