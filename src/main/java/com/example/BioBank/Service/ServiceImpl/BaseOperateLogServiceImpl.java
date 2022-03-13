package com.example.BioBank.Service.ServiceImpl;

import com.example.BioBank.DTO.BaseOperateLogDTO;
import com.example.BioBank.Dao.BaseOperateLogDao;
import com.example.BioBank.Response.Response;
import com.example.BioBank.Service.BaseOperateLogService;
import com.example.BioBank.enums.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseOperateLogServiceImpl implements BaseOperateLogService {
    private static final Logger logger = LoggerFactory.getLogger(BaseOperateLogServiceImpl.class);

    @Autowired
    private BaseOperateLogDao baseOperateLogDao;

    @Override
    public Response<List<BaseOperateLogDTO>> getBaseOperateLogInOneWeek(){
        Response<List<BaseOperateLogDTO>> response = new Response<>();
        List<BaseOperateLogDTO> baseOperateLogDTOList = baseOperateLogDao.findBaseOperateLogInOneWeek();
        if(baseOperateLogDTOList.isEmpty()){
            logger.warn("[BaseOperateLogs In One Week Are Not Found]");
            response.setFail(ResponseEnum.BASE_OPERATE_LOG_SEARCH_NOT_FOUND);
            return response;
        }
        response.setSuc(baseOperateLogDTOList);
        return response;
    }

    @Override
    public Response<List<BaseOperateLogDTO>> getAllBaseOperateLog(){
        Response<List<BaseOperateLogDTO>> response = new Response<>();
        List<BaseOperateLogDTO> baseOperateLogDTOList = baseOperateLogDao.findAllBaseOperateLog();
        if(baseOperateLogDTOList.isEmpty()){
            logger.warn("[BaseOperateLogs Are Not Found]");
            response.setFail(ResponseEnum.BASE_OPERATE_LOG_SEARCH_NOT_FOUND);
            return response;
        }
        response.setSuc(baseOperateLogDTOList);
        return response;
    }

    @Override
    public Response<List<BaseOperateLogDTO>> getBaseOperateLogByOperateDescription(String operateDescription){
        Response<List<BaseOperateLogDTO>> response = new Response<>();
        List<BaseOperateLogDTO> baseOperateLogDTOList = baseOperateLogDao.findBaseOperateLogByOperateDescription(operateDescription);
        if(baseOperateLogDTOList.isEmpty()){
            logger.warn("[BaseOperateLogs Related Are Not Found], operateDescription: {}", operateDescription);
            response.setFail(ResponseEnum.BASE_OPERATE_LOG_SEARCH_NOT_FOUND);
            return response;
        }
        response.setSuc(baseOperateLogDTOList);
        return response;
    }
}
