package com.example.BioBank.Service;

import com.example.BioBank.DTO.BaseOperateLogDTO;
import com.example.BioBank.Response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseOperateLogService {
    Response<List<BaseOperateLogDTO>> getBaseOperateLogInOneWeek();

    Response<List<BaseOperateLogDTO>> getAllBaseOperateLog();

    Response<List<BaseOperateLogDTO>> getBaseOperateLogByOperateDescription(String operateDescription);
}
