package com.example.BioBank.Controller;

import com.example.BioBank.DTO.BioModelDTO;
import com.example.BioBank.DTO.BioModelInfoDTO;
import com.example.BioBank.Entity.BioModel;
import com.example.BioBank.Entity.BioModelSQLConditions;
import com.example.BioBank.Exception.BioBankRuntimeException;
import com.example.BioBank.Response.Response;
import com.example.BioBank.Service.BioModelService;
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

@Api(tags = "生物样品Controller")
@RestController
@RequestMapping("/bioModel")
public class BioModelController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(BioModelController.class);

    @Autowired
    BioModelService bioModelService;

    @PostMapping("/storeNewBioModel")
    @ApiOperation("存入新的生物样本")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bioModel", value = "存入的生物样品信息", paramType = "query", dataType = "BioModel"),
    })
    public Response<Boolean> storeNewBioModel(@RequestBody BioModel bioModel) {
        Response<Boolean> response = new Response<>();
        try {
            long userId =getUserId();
            return bioModelService.storeNewBioModel(userId, bioModel);
        } catch (IllegalArgumentException e) {
            logger.warn("[storeNewBioModel Illegal Argument], bioModel: {}", SerialUtil.toJsonStr(bioModel), e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[storeNewBioModel Runtime Exception], bioModel: {}", SerialUtil.toJsonStr(bioModel), e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[storeNewBioModel Exception], bioModel: {}", SerialUtil.toJsonStr(bioModel), e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getBioModelInfoByModelId")
    @ApiOperation("获得生物样品信息By样品id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelId", value = "生物样品id", paramType = "query", dataType = "long"),
    })
    public Response<BioModelInfoDTO> getBioModelInfoByModelId(@RequestParam("modelId") long modelId) {
        Response<BioModelInfoDTO> response = new Response<>();
        try {
            long userId = getUserId();
            return bioModelService.getBioModelInfoByModelId(userId,modelId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getBioModelInfoByModelId Illegal Argument], modelId: {}", modelId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getBioModelInfoByModelId Runtime Exception], modelId: {}", modelId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getBioModelInfoByModelId Exception], modelId: {}", modelId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getBioModelModifiedInOneWeek")
    @ApiOperation("获得一周内修改过的生物样本列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankId", value = "生物样品库id", paramType = "query", dataType = "long"),
    })
    public Response<List<BioModelDTO>> getBioModelModifiedInOneWeek(@RequestParam("bankId") long bankId) {
        Response<List<BioModelDTO>> response = new Response<>();
        try {
            long userId = getUserId();
            return bioModelService.getBioModelModifiedInOneWeek(userId, bankId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getBioModelModifiedInOneWeek Illegal Argument], bankId: {}", bankId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getBioModelModifiedInOneWeek Runtime Exception], bankId: {}", bankId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getBioModelModifiedInOneWeek Exception], bankId: {}", bankId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getBioModelByConditions")
    @ApiOperation("获得条件集生物样本列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bioModelSQLConditions", value = "搜索样本列表条件集", paramType = "query", dataType = "BioModelSQLConditions"),
    })
    public Response<List<BioModelDTO>> getBioModelByConditions(@RequestBody BioModelSQLConditions bioModelSQLConditions) {
        Response<List<BioModelDTO>> response = new Response<>();
        try {
            long userId = getUserId();
            return bioModelService.getBioModelByConditions(userId, bioModelSQLConditions);
        } catch (IllegalArgumentException e) {
            logger.warn("[getBioModelByConditions Illegal Argument], bioModelSQLConditions: {}", SerialUtil.toJsonStr(bioModelSQLConditions), e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getBioModelByConditions Runtime Exception], bioModelSQLConditions: {}", SerialUtil.toJsonStr(bioModelSQLConditions), e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getBioModelByConditions Exception], bioModelSQLConditions: {}", SerialUtil.toJsonStr(bioModelSQLConditions), e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getBioModelInsertInOneWeek")
    @ApiOperation("获得一周内新增的生物样本列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankId", value = "生物样品库id", paramType = "query", dataType = "long"),
    })
    public Response<List<BioModelDTO>> getBioModelInsertInOneWeek(@RequestParam("bankId") long bankId) {
        Response<List<BioModelDTO>> response = new Response<>();
        try {
            long userId = getUserId();
            return bioModelService.getBioModelInsertInOneWeek(userId, bankId);
        } catch (IllegalArgumentException e) {
            logger.warn("[getBioModelInsertInOneWeek Illegal Argument], bankId: {}", bankId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getBioModelInsertInOneWeek Runtime Exception], bankId: {}", bankId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getBioModelInsertInOneWeek Exception], bankId: {}", bankId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getBioModelByTemperatureLimit")
    @ApiOperation("获得温度限制范围内的生物样本列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankId", value = "生物样品库id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "modelTemperatureMost", value = "生物样品库id", paramType = "query", dataType = "float"),
            @ApiImplicitParam(name = "modelTemperatureLeast", value = "生物样品库id", paramType = "query", dataType = "float"),
    })
    public Response<List<BioModelDTO>> getBioModelByTemperatureLimit(@RequestParam("bankId") long bankId, @RequestParam("modelTemperatureMost") float modelTemperatureMost, @RequestParam("modelTemperatureLeast") float modelTemperatureLeast) {
        Response<List<BioModelDTO>> response = new Response<>();
        try {
            long userId = getUserId();
            return bioModelService.getBioModelByTemperatureLimit(userId, bankId, modelTemperatureMost, modelTemperatureLeast);
        } catch (IllegalArgumentException e) {
            logger.warn("[getBioModelByTemperatureLimit Illegal Argument], bankId: {}, modelTemperatureMost: {}, modelTemperatureLeast: {}", bankId, modelTemperatureMost, modelTemperatureLeast, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[getBioModelInsertInOneWeek Runtime Exception], bankId: {}, modelTemperatureMost: {}, modelTemperatureLeast: {}", bankId, modelTemperatureMost, modelTemperatureLeast, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getBioModelInsertInOneWeek Exception], bankId: {}, modelTemperatureMost: {}, modelTemperatureLeast: {}", bankId, modelTemperatureMost, modelTemperatureLeast, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateBioModel")
    @ApiOperation("更新生物样品属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bioModelSQLConditions", value = "更新的属性值", paramType = "query", dataType = "BioModelSQLConditions"),
    })
    public Response<Boolean> updateBioModel(@RequestBody BioModelSQLConditions bioModelSQLConditions) {
        Response<Boolean> response = new Response<>();
        try {
            long userId = getUserId();
            return bioModelService.updateBioModel(userId, bioModelSQLConditions);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateBioModel Illegal Argument], bioModelSQLConditions: {}", SerialUtil.toJsonStr(bioModelSQLConditions), e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[updateBioModel Runtime Exception], bioModelSQLConditions: {}", SerialUtil.toJsonStr(bioModelSQLConditions), e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateBioModel Exception], bioModelSQLConditions: {}", SerialUtil.toJsonStr(bioModelSQLConditions), e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateBioModelTemperatureLimit")
    @ApiOperation("更新生物样品温度限制")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankId", value = "样本库id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "modelId", value = "样本id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "newModelTemperatureMost", value = "新的最高温限制", paramType = "query", dataType = "float"),
            @ApiImplicitParam(name = "newModelTemperatureLeast", value = "新的最低温限制", paramType = "query", dataType = "float"),
    })
    public Response<Boolean> updateBioModelTemperatureLimit(@RequestParam("bankId") long bankId, @RequestParam("modelId") long modelId,
                                                            @RequestParam(value = "newModelTemperatureMost") float newModelTemperatureMost,
                                                            @RequestParam(value = "newModelTemperatureLeast") float newModelTemperatureLeast) {
        Response<Boolean> response = new Response<>();
        try {
            long userId = getUserId();
            return bioModelService.updateBioModelTemperatureLimit(userId, bankId, modelId, newModelTemperatureMost, newModelTemperatureLeast);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateBioModelTemperatureLimit Illegal Argument], bankId: {}, modelId: {}, newModelTemperatureMost: {}, newModelTemperatureLeast: {}",
                    bankId, modelId, newModelTemperatureMost, newModelTemperatureLeast, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[updateBioModelTemperatureLimit Runtime Exception], bankId: {}, modelId: {}, newModelTemperatureMost: {}, newModelTemperatureLeast: {}",
                    bankId, modelId, newModelTemperatureMost, newModelTemperatureLeast, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateBioModelTemperatureLimit Exception], bankId: {}, modelId: {}, newModelTemperatureMost: {}, newModelTemperatureLeast: {}",
                    bankId, modelId, newModelTemperatureMost, newModelTemperatureLeast, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateUserModelRelation")
    @ApiOperation("更新用户-样品操作权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modifiedUserId", value = "待更新的用户id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "modelId", value = "样品id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "newUMPriority", value = "该用户新的用户—样品操作权限", paramType = "query", dataType = "int"),
    })
    public Response<Boolean> updateUserModelRelation(@RequestParam("modifiedUserId") long modifiedUserId, @RequestParam("modelId") long modelId, @RequestParam("newUMPriority") int newUMPriority) {
        Response<Boolean> response = new Response<>();
        try {
            long userId = getUserId();
            return bioModelService.updateUserModelRelation(userId, modifiedUserId, modelId, newUMPriority);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateUserModelRelation Illegal Argument], modifiedUserId: {}, modelId: {}, newUMPriority: {}", modifiedUserId, modelId, newUMPriority, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[updateUserModelRelation Runtime Exception], modifiedUserId: {}, modelId: {}, newUMPriority: {}", modifiedUserId, modelId, newUMPriority, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateUserModelRelation Exception], modifiedUserId: {}, modelId: {}, newUMPriority: {}", modifiedUserId, modelId, newUMPriority, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/deleteBioModelByModelId")
    @ApiOperation("删除生物样品By modelId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankId", value = "待删除样本所属的样本库id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "modelId", value = "待删除的样本id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> deleteBioModelByModelId(@RequestParam("bankId") long bankId, @RequestParam("modelId") long modelId) {
        Response<Boolean> response = new Response<>();
        try {
            long userId = getUserId();
            return bioModelService.deleteBioModelByModelId(userId, bankId, modelId);
        } catch (IllegalArgumentException e) {
            logger.warn("[deleteBioModelByModelId Illegal Argument], bankId: {}, modelId: {}", bankId, modelId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (BioBankRuntimeException e) {
            logger.error("[deleteBioModelByModelId Runtime Exception], bankId: {}, modelId: {}", bankId, modelId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[deleteBioModelByModelId Exception], bankId: {}, modelId: {}", bankId, modelId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
}
