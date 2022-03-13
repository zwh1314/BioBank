package com.example.BioBank.Controller;



import com.example.BioBank.DTO.OperateLogDTO;
import com.example.BioBank.Entity.ModelOperateLogSQLConditions;
import com.example.BioBank.Exception.BioBankRuntimeException;

import com.example.BioBank.RedisService.OperateLogRedisService;
import com.example.BioBank.Response.Response;

import com.example.BioBank.Service.OperateLogService;
import com.example.BioBank.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "样品操作日志Controller")
@RestController
@RequestMapping("/operateLog")
public class OperateLogController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(OperateLogController.class);

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private OperateLogRedisService operateLogRedisService;

    @GetMapping("/getOperateLogByConditions")
    @ApiOperation("获得生物样品操作日志记录通过多个搜索条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelOperateLogSelectConditions", value = "生物样品操作日志搜索条件", paramType = "query", dataType = "ModelOperateLogSelectConditions"),
    })
    public Response<List<OperateLogDTO>> getOperateLogByConditions(@RequestBody ModelOperateLogSQLConditions modelOperateLogSQLConditions) {
            Response<List<OperateLogDTO>> response = new Response<>();
            try {
                return operateLogService.getOperateLogByConditions(modelOperateLogSQLConditions);
            } catch (IllegalArgumentException e) {
                logger.warn("[getOperateLogByConditions Illegal Argument], publisherId: {}", modelOperateLogSQLConditions, e);
                response.setFail(ResponseEnum.ILLEGAL_PARAM);
                return response;
            } catch (BioBankRuntimeException e) {
                logger.error("[getOperateLogByConditions Runtime Exception], publisherId: {}", modelOperateLogSQLConditions, e);
                response.setFail(e.getExceptionCode(), e.getMessage());
                return response;
            }  catch (Exception e) {
                logger.error("[getOperateLogByConditions Exception], publisherId: {}", modelOperateLogSQLConditions, e);
                response.setFail(ResponseEnum.SERVER_ERROR);
                return response;
            }
    }

    @GetMapping("/getOperateLogInOneWeek")
    @ApiOperation("获得一周内的生物样品操作日志记录")
    public Response<List<OperateLogDTO>> getOperateLogInOneWeek() {
        Response<List<OperateLogDTO>> response = new Response<>();
        try {
            return operateLogService.getOperateLogInOneWeek();
        } catch (BioBankRuntimeException e) {
            logger.error("[getOperateLogInOneWeek Runtime Exception]",  e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getOperateLogInOneWeek Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
}
