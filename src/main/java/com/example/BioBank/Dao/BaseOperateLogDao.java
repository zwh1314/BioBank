package com.example.BioBank.Dao;

import com.example.BioBank.DTO.BaseOperateLogDTO;
import com.example.BioBank.Entity.BaseOperateLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BaseOperateLogDao {
    @Insert("INSERT INTO base_operate_log(operate_description, operate_time) " +
            "VALUES (#{operateDescription}, #{commentLike}, NOW() )")
    void insertBaseOperateLog(BaseOperateLog baseOperateLog);

    @ResultType(BaseOperateLogDTO.class)
    @Select("SELECT base_operate_log_id as baseOperateLogId, operate_description as operateDescription, operate_time as operateTime " +
            "FROM base_operate_log WHERE TO_DAYS(NOW()) - TO_DAYS(operate_time) <= 7")
    List<BaseOperateLogDTO> findBaseOperateLogInOneWeek();

    @ResultType(BaseOperateLogDTO.class)
    @Select("SELECT base_operate_log_id as baseOperateLogId, operate_description as operateDescription, operate_time as operateTime FROM base_operate_log")
    List<BaseOperateLogDTO> findAllBaseOperateLog();

    @ResultType(BaseOperateLogDTO.class)
    @Select("SELECT base_operate_log_id as baseOperateLogId, operate_description as operateDescription, operate_time as operateTime " +
            "FROM base_operate_log WHERE operate_description LIKE CONCAT('%',#{operateDescription},'%')")
    List<BaseOperateLogDTO> findBaseOperateLogByOperateDescription(@Param("operateDescription") String operateDescription);

    @Delete("Delete From base_operate_log WHERE TO_DAYS(NOW()) - TO_DAYS(operate_time) > 31")
    void deleteBaseOperateLogOverOneMonth();
}
