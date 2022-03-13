package com.example.BioBank.Controller;

import com.example.BioBank.DTO.BaseOperateLogDTO;
import com.example.BioBank.Exception.BioBankRuntimeException;
import com.example.BioBank.Response.Response;
import com.example.BioBank.Service.BaseOperateLogService;
import com.example.BioBank.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "基本操作日志Controller")
@RestController
@RequestMapping("/baseOperateLog")
public class BaseOperateLogController {
    private static final Logger logger = LoggerFactory.getLogger(BaseOperateLogController.class);

    @Autowired
    private BaseOperateLogService baseOperateLogService;

    @GetMapping("/getBaseOperateLogInOneWeek")
    @ApiOperation("获得一周内的所有基本操作日志记录")
    public Response<List<BaseOperateLogDTO>> getBaseOperateLogInOneWeek(){
        Response<List<BaseOperateLogDTO>> response = new Response<>();
        try {
            return baseOperateLogService.getBaseOperateLogInOneWeek();
        } catch (BioBankRuntimeException e) {
            logger.error("[getBaseOperateLogInOneWeek Runtime Exception]", e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("[getBaseOperateLogInOneWeek Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getAllBaseOperateLog")
    @ApiOperation("获得所有的基本操作日志记录")
    public Response<List<BaseOperateLogDTO>> getAllBaseOperateLog(){
        Response<List<BaseOperateLogDTO>> response = new Response<>();
        try {
            return baseOperateLogService.getAllBaseOperateLog();
        } catch (BioBankRuntimeException e) {
            logger.error("[getAllBaseOperateLog Runtime Exception]", e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("[getAllBaseOperateLog Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getBaseOperateLogByOperateDescription")
    @ApiOperation("获得与该基本操作描述相关的基本操作日志记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "operateDescription", value = "基本操作描述", paramType = "query", dataType = "String"),
    })
    public Response<List<BaseOperateLogDTO>> getBaseOperateLogByOperateDescription(@RequestParam("operateDescription") String operateDescription){
        Response<List<BaseOperateLogDTO>> response = new Response<>();
        try {
            return baseOperateLogService.getBaseOperateLogByOperateDescription(operateDescription);
        } catch (IllegalArgumentException e) {
            logger.warn("[getBaseOperateLogByOperateDescription Illegal Argument], operateDescription: {}", operateDescription, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getBaseOperateLogByOperateDescription Runtime Exception]", e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("[getBaseOperateLogByOperateDescription Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
}
