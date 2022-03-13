package com.example.BioBank.Controller;

import com.example.BioBank.DTO.ModelBankDTO;
import com.example.BioBank.Entity.ModelBankSQLConditions;
import com.example.BioBank.Exception.BioBankRuntimeException;
import com.example.BioBank.Response.Response;
import com.example.BioBank.Service.ModelBankService;
import com.example.BioBank.enums.ResponseEnum;
import com.example.BioBank.utils.SerialUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "样品库Controller")
@RestController
@RequestMapping("modelBank")
public class ModelBankController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ModelBankController.class);

    @Autowired
    private ModelBankService modelBankService;


    @GetMapping("/getModelBankByConditions")
    @ApiOperation("获得生物样品库通过多个查找条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelBankSQLConditions", value = "生物样品库查找条件", paramType = "query", dataType = "ModelBankSQLConditions"),
    })
    public Response<List<ModelBankDTO>> getModelBankByConditions(@RequestBody ModelBankSQLConditions modelBankSQLConditions) {
        Response<List<ModelBankDTO>> response = new Response<>();
        try {
            long userId = getUserId();
            return modelBankService.getModelBankByConditions(userId, modelBankSQLConditions);
        } catch (IllegalArgumentException e) {
            logger.warn("[getModelBankByConditions Illegal Argument], modelBankSQLConditions: {}", SerialUtil.toJsonStr(modelBankSQLConditions), e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getModelBankByConditions Runtime Exception], modelBankSQLConditions: {}", SerialUtil.toJsonStr(modelBankSQLConditions), e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getModelBankByConditions Exception], modelBankSQLConditions: {}", SerialUtil.toJsonStr(modelBankSQLConditions), e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getModelBankModifiedInOneWeek")
    @ApiOperation("获得一周内被修改过的生物样品库")
    public Response<List<ModelBankDTO>> getModelBankModifiedInOneWeek() {
        Response<List<ModelBankDTO>> response = new Response<>();
        try {
            return modelBankService.getModelBankModifiedInOneWeek();
        } catch (BioBankRuntimeException e) {
            logger.error("[getModelBankModifiedInOneWeek Runtime Exception]",  e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getModelBankModifiedInOneWeek Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getModelBankIsFullOrNot")
    @ApiOperation("判断生物样品库是否已满")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankId", value = "生物样品库id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> getModelBankIsFullOrNot(@RequestParam("bankId") long bankId) {
        Response<Boolean> response = new Response<>();
        try {
            return modelBankService.getModelBankIsFullOrNot(bankId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getModelBankIsFullOrNot Illegal Argument], bankId: {}", bankId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getModelBankIsFullOrNot Runtime Exception], bankId: {}", bankId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getModelBankIsFullOrNot Exception], bankId: {}", bankId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/createNewModelBank")
    @ApiOperation("创建新的生物样本库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankCapacity", value = "样品库容量", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "bankName", value = "样品库名称", paramType = "query", dataType = "String"),
    })
    public Response<Boolean> createNewModelBank(@RequestParam(value = "bankCapacity")int bankCapacity,
                                         @RequestParam(value = "bankName")String bankName) {
        Response<Boolean> response = new Response<>();
        try {
            long userId =getUserId();
            return modelBankService.createModelBank(userId, bankCapacity, bankName);
        } catch (IllegalArgumentException e) {
            logger.warn("[createNewModelBank Illegal Argument], bankCapacity: {}, bankName: {}", bankCapacity, bankName, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[createNewModelBank Runtime Exception], bankCapacity: {}, bankName: {}", bankCapacity, bankName, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[createNewModelBank Exception], bankCapacity: {}, bankName: {}", bankCapacity, bankName, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateModelBank")
    @ApiOperation("更新生物样品库属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelBankSQLConditions", value = "更新的属性值", paramType = "query", dataType = "ModelBankSQLConditions"),
    })
    public Response<Boolean> updateModelBank(@RequestBody ModelBankSQLConditions modelBankSQLConditions) {
        Response<Boolean> response = new Response<>();
        try {
            long userId = getUserId();
            return modelBankService.updateModelBank(userId, modelBankSQLConditions);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateModelBank Illegal Argument], modelBankSQLConditions {}", SerialUtil.toJsonStr(modelBankSQLConditions), e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[updateModelBank Runtime Exception], modelBankSQLConditions {}", SerialUtil.toJsonStr(modelBankSQLConditions), e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateModelBank Exception], modelBankSQLConditions {}", SerialUtil.toJsonStr(modelBankSQLConditions), e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateUserBankRelation")
    @ApiOperation("更新用户-样品库操作权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modifiedUserId", value = "待更新的用户id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "bankId", value = "样品库id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "newUBPriority", value = "该用户新的用户—样品库操作权限", paramType = "query", dataType = "int"),
    })
    public Response<Boolean> updateUserBankRelation(@RequestParam("modifiedUserId") long modifiedUserId, @RequestParam("bankId") long bankId, @RequestParam("newUBPriority") int newUBPriority) {
        Response<Boolean> response = new Response<>();
        try {
            long userId = getUserId();
            return modelBankService.updateUserBankRelation(userId, modifiedUserId, bankId, newUBPriority);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateUserBankRelation Illegal Argument], modifiedUserId: {}, bankId: {}, newUBPriority: {}", modifiedUserId, bankId, newUBPriority, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[updateUserBankRelation Runtime Exception], modifiedUserId: {}, bankId: {}, newUBPriority: {}", modifiedUserId, bankId, newUBPriority, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateUserBankRelation Exception], modifiedUserId: {}, bankId: {}, newUBPriority: {}", modifiedUserId, bankId, newUBPriority, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/deleteModelBankByBankId")
    @ApiOperation("删除生物样品库By bankId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankId", value = "待删除的样本库id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> deleteModelBankByBankId(@RequestParam("bankId") long bankId) {
        Response<Boolean> response = new Response<>();
        try {
            long userId = getUserId();
            return modelBankService.deleteModelBankByBankId(userId, bankId);
        } catch (IllegalArgumentException e) {
            logger.warn("[deleteModelBankByBankId Illegal Argument], bankId: {}", bankId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[deleteModelBankByBankId Runtime Exception], bankId: {}", bankId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[deleteModelBankByBankId Exception], bankId: {}", bankId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
}
