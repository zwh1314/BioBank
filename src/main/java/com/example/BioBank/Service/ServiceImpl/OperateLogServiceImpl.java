package com.example.BioBank.Service.ServiceImpl;

import com.example.BioBank.DTO.OperateLogDTO;
import com.example.BioBank.Dao.OperateLogDao;
import com.example.BioBank.Entity.ModelOperateLogSQLConditions;
import com.example.BioBank.Response.Response;
import com.example.BioBank.Service.OperateLogService;
import com.example.BioBank.enums.ResponseEnum;
import com.example.BioBank.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {
    private static final Logger logger = LoggerFactory.getLogger(OperateLogServiceImpl.class);

    @Autowired
    private OperateLogDao operateLogDao;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Response<List<OperateLogDTO>> getOperateLogByConditions(ModelOperateLogSQLConditions operateLogSelectConditions){
        Response<List<OperateLogDTO>> response=new Response<>();

        List<OperateLogDTO> operateLogDTOList = operateLogDao.findModelOperateLogByConditions(operateLogSelectConditions);
        if (operateLogDTOList.isEmpty()) {
            logger.warn("[ModelOperateLogs In These Conditions Are Not Found]");
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        }
        else{
            response.setSuc(operateLogDTOList);
        }

        return response;
    }

    @Override
    public Response<List<OperateLogDTO>> getOperateLogInOneWeek(){
        Response<List<OperateLogDTO>> response=new Response<>();

        List<OperateLogDTO> operateLogDTOList = operateLogDao.findModelOperateLogInOneWeek();
        if (operateLogDTOList.isEmpty()) {
            logger.warn("[ModelOperateLogs In One Week Are Not Found]");
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        }
        else{
            response.setSuc(operateLogDTOList);
        }
        return response;
    }
}
