package com.example.BioBank.Service;

import com.example.BioBank.DTO.OperateLogDTO;
import com.example.BioBank.Entity.ModelOperateLogSQLConditions;
import com.example.BioBank.Response.Response;

import java.util.List;

public interface OperateLogService {
    Response<List<OperateLogDTO>> getOperateLogByConditions(ModelOperateLogSQLConditions operateLogSelectConditions);

    Response<List<OperateLogDTO>> getOperateLogInOneWeek();

}
