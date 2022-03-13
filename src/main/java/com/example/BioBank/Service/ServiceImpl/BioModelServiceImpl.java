package com.example.BioBank.Service.ServiceImpl;

import com.example.BioBank.DTO.*;
import com.example.BioBank.Dao.*;
import com.example.BioBank.Entity.*;
import com.example.BioBank.Exception.BioBankRuntimeException;
import com.example.BioBank.Response.Response;
import com.example.BioBank.Service.BioModelService;
import com.example.BioBank.enums.OperatePriorityEnum;
import com.example.BioBank.enums.OperateTypeEnum;
import com.example.BioBank.enums.ResponseEnum;
import com.example.BioBank.enums.UserPriorityEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BioModelServiceImpl implements BioModelService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private BioModelDao bioModelDao;

    @Autowired
    private UBRelationDao ubRelationDao;

    @Autowired
    private UMRelationDao umRelationDao;

    @Autowired
    private OperateLogDao operateLogDao;

    @Autowired
    private BaseOperateLogDao baseOperateLogDao;

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> storeNewBioModel(long userId, BioModel bioModel){
        Response<Boolean> response = new Response<>();
        long bankId = bioModel.getBankId();

        //查询该用户是否有该生物库的改动权限
        if(validateModelBankModifyPriority(userId,bankId)){
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        //查找该库的所有者
        UserBankRelationDTO userBankRelationDTOOwner = ubRelationDao.getBankOwner(bankId);
        //存入新的生物样品
        try {
            bioModel.setModelAccessible(OperatePriorityEnum.BIO_MODEL_ACCESSIBLE.getValue());
            //存入新的生物样品
            bioModelDao.insertBioModelInfo(bioModel);

            UserModelRelation userModelRelation = constructNewUMRelation(userId);
            userModelRelation.setUmRelationId(OperatePriorityEnum.UM_RELATION_PRIORITY_7.getValue());
            //更新modelId
            userModelRelation.setModelId(bioModel.getModelId());
            //新建用户-样品库权限关系
            umRelationDao.insertUserModelRelation(userModelRelation);
            if(userBankRelationDTOOwner.getUserId() != userId){
                userModelRelation.setUserId(userBankRelationDTOOwner.getUserId());
                umRelationDao.insertUserModelRelation(userModelRelation);
            }
            //插入存入生物样品日志记录
            OperateLog operateLog = constructNewOperateLog(userId,bankId);
            operateLog.setOperateType(OperateTypeEnum.ADD.getCode());
            operateLog.setModelId(bioModel.getModelId());
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e){
            logger.warn("[createModelBank Operate DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    @Override
    public Response<BioModelInfoDTO> getBioModelInfoByModelId(long userId, long modelId){
        Response<BioModelInfoDTO> response=new Response<>();

        //查询该用户是否有该生物样本的访问权限
        if(validateBioModelSearchPriority(userId, modelId)){
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        BioModelInfoDTO bioModelInfoDTO = bioModelDao.findBioModelInfoByModelId(modelId);

        try {
            //插入搜索生物样品日志记录
            OperateLog operateLog = constructNewOperateLog(userId,bioModelInfoDTO);
            operateLog.setOperateType(OperateTypeEnum.SEARCH.getCode());
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e){
            logger.warn("[insertOperateLog Operate DataBase Fail]", e);
        }

        response.setSuc(bioModelInfoDTO);
        return response;
    }

    @Override
    public  Response<List<BioModelDTO>>getBioModelByConditions(long userId, BioModelSQLConditions bioModelSQLConditions) {
        Response<List<BioModelDTO>> response = new Response<>();

        //验证用户的生物样品库访问权限
        if(validateModelBankSearchPriority(userId,bioModelSQLConditions.getBankId())){
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        List<BioModelDTO> bioModelDTOList = bioModelDao.findBioModelByConditions(bioModelSQLConditions);
        if(bioModelDTOList.size() == 0){
            response.setFail(ResponseEnum.OBJECT_RELATED_NOT_FOUND);
            return response;
        }

        try {
            //插入搜索生物样品库日志记录
            OperateLog operateLog = constructNewOperateLog(userId,bioModelSQLConditions.getBankId());
            operateLog.setOperateType(OperateTypeEnum.SEARCH.getCode());
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e){
            logger.warn("[insertOperateLog Operate DataBase Fail]", e);
        }

        response.setSuc(bioModelDTOList);
        return response;
    }

    @Override
    public  Response<List<BioModelDTO>>getBioModelModifiedInOneWeek(long userId, long bankId) {
        Response<List<BioModelDTO>> response = new Response<>();

        //验证用户的生物样品库访问权限
        if(validateModelBankSearchPriority(userId,bankId)){
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        List<BioModelDTO> bioModelDTOList = bioModelDao.findBioModelModifiedInOneWeek();
        if(bioModelDTOList.size() == 0){
            response.setFail(ResponseEnum.OBJECT_RELATED_NOT_FOUND);
            return response;
        }

        try {
            //插入搜索生物样品库日志记录
            OperateLog operateLog = constructNewOperateLog(userId,bankId);
            operateLog.setOperateType(OperateTypeEnum.SEARCH.getCode());
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e){
            logger.warn("[insertOperateLog Operate DataBase Fail]", e);
        }

        response.setSuc(bioModelDTOList);
        return response;
    }

    @Override
    public  Response<List<BioModelDTO>>getBioModelInsertInOneWeek(long userId, long bankId) {
        Response<List<BioModelDTO>> response = new Response<>();

        //验证用户的生物样品库访问权限
        if(validateModelBankSearchPriority(userId,bankId)){
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        List<BioModelDTO> bioModelDTOList = bioModelDao.findBioModelInsertInOneWeek();
        if(bioModelDTOList.size() == 0){
            response.setFail(ResponseEnum.OBJECT_RELATED_NOT_FOUND);
            return response;
        }

        try {
            //插入搜索生物样品库日志记录
            OperateLog operateLog = constructNewOperateLog(userId,bankId);
            operateLog.setOperateType(OperateTypeEnum.SEARCH.getCode());
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e){
            logger.warn("[insertOperateLog Operate DataBase Fail]", e);
        }

        response.setSuc(bioModelDTOList);
        return response;
    }

    @Override
    public  Response<List<BioModelDTO>>getBioModelByTemperatureLimit(long userId, long bankId, float modelTemperatureMost, float modelTemperatureLeast) {
        Response<List<BioModelDTO>> response = new Response<>();

        //验证用户的生物样品库访问权限
        if(validateModelBankSearchPriority(userId,bankId)){
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        List<BioModelDTO> bioModelDTOList = bioModelDao.findBioModelByTemperatureLimit(modelTemperatureMost,modelTemperatureLeast);
        if(bioModelDTOList.size() == 0){
            response.setFail(ResponseEnum.OBJECT_RELATED_NOT_FOUND);
            return response;
        }

        try {
            //插入搜索生物样品库日志记录
            OperateLog operateLog = constructNewOperateLog(userId,bankId);
            operateLog.setOperateType(OperateTypeEnum.SEARCH.getCode());
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e){
            logger.warn("[insertOperateLog Operate DataBase Fail]", e);
        }

        response.setSuc(bioModelDTOList);
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> updateBioModel(long userId, BioModelSQLConditions bioModelSQLConditions){
        Response<Boolean> response = new Response<>();

        //检验用户更新该生物样品的权限
        if(validateBioModelModifyPriority(userId,bioModelSQLConditions.getBankId())){
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        try {
            //改动生物样品属性
            bioModelDao.updateConditions(bioModelSQLConditions);

            //插入修改生物样品日志记录
            OperateLog operateLog = constructNewOperateLog(userId,bioModelSQLConditions.getBankId());
            operateLog.setOperateType(OperateTypeEnum.MODIFY.getCode());
            operateLog.setModelId(bioModelSQLConditions.getModelId());
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e){
            logger.warn("[updateBioModel Operate DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> updateBioModelTemperatureLimit(long userId, long bankId, long modelId, float newModelTemperatureMost, float newModelTemperatureLeast){
        Response<Boolean> response = new Response<>();

        //检验用户更新该生物样品的权限
        if(validateBioModelModifyPriority(userId,bankId)){
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        try {
            //改动生物样品属性
            bioModelDao.updateModelTemperatureLimit(modelId,newModelTemperatureMost, newModelTemperatureLeast);

            //插入修改生物样品日志记录
            OperateLog operateLog = constructNewOperateLog(userId,bankId);
            operateLog.setOperateType(OperateTypeEnum.MODIFY.getCode());
            operateLog.setModelId(modelId);
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e){
            logger.warn("[updateBioModel Operate DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> updateUserModelRelation(long userId, long modifiedUserId, long modelId, int newUMPriority){
        Response<Boolean> response = new Response<>();

        //检验用户更新该用户-生物样品权限的权限
        UserModelRelationDTO userModelRelationDTO = umRelationDao.getUserModelRelationByUserIdAndModelId(userId, modelId);
        int operatePriority = userModelRelationDTO.getUmPriority();
        if (operatePriority < OperatePriorityEnum.UM_RELATION_PRIORITY_7.getValue()) {
            logger.warn("[User's Priority Is Not Enough], User's Priority: {}", OperatePriorityEnum.getPriorityMsgByPriority(operatePriority));
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }
        if (newUMPriority > OperatePriorityEnum.UM_RELATION_PRIORITY_3.getValue()) {
            logger.warn("[Operate-Remove Priority Only Belongs To The Owner]");
            response.setFail(ResponseEnum.OPERATE_REMOVE_PRIORITY_ONLY_BELONGS_TO_THE_OWNER);
            return response;
        }

        UserModelRelationDTO modifiedUserModelRelationDTO = umRelationDao.getUserModelRelationByUserIdAndModelId(modifiedUserId, modelId);
        try {
            BaseOperateLog baseOperateLog = new BaseOperateLog();
            baseOperateLog.setOperateDescription("用户: " + userId + ", 将用户: " + modifiedUserId + " 对生物样品: " + modelId + ", 的操作权限更新为: " + OperatePriorityEnum.getPriorityMsgByPriority(newUMPriority));
            if (modifiedUserModelRelationDTO == null) {
                //插入用户-样本操作权限
                UserModelRelation userModelRelation = constructNewUserModelRelation(modifiedUserId, modelId, newUMPriority);
                //更新生物样品库
                umRelationDao.insertUserModelRelation(userModelRelation);
                //插入基本操作日志记录
                baseOperateLogDao.insertBaseOperateLog(baseOperateLog);
            } else {
                //更新用户-样本库操作权限
                //更新生物样品库
                umRelationDao.updateUMPriority(modifiedUserId, modelId, newUMPriority);
                //插入基本操作日志记录
                baseOperateLogDao.insertBaseOperateLog(baseOperateLog);
            }
        } catch (Exception e){
            logger.warn("[updateUserModelRelation Operate DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> deleteBioModelByModelId(long userId, long bankId, long modelId){
        Response<Boolean> response = new Response<>();

        //查询用户删除权限
        UserModelRelationDTO userModelRelationDTO = umRelationDao.getUserModelRelationByUserIdAndModelId(userId, modelId);
        UserDTO userDTO = userDao.getUserByUserId(userId);
        int operatePriority = userModelRelationDTO.getUmPriority();
        if ((validateModelBankModifyPriority(userId,bankId) || (operatePriority >> 2) % 2 == 0) && userDTO.getPriority() < UserPriorityEnum.SUPERADMINISTRATOR.getPriority()) {
            logger.warn("[User's Priority Is Not Enough], User's Priority: {}", OperatePriorityEnum.getPriorityMsgByPriority(operatePriority));
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        try {
            //删除样本
            OperateLog operateLog = constructNewOperateLog(userId,bankId);
            operateLog.setOperateType(OperateTypeEnum.REMOVE.getCode());
            operateLog.setModelId(modelId);
            bioModelDao.deleteBioModelByModelId(modelId);
            //插入删除生物样品日志记录
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e) {
            logger.warn("[deleteBioModelByModelId Delete DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    private UserModelRelation constructNewUMRelation(long userId){
        UserModelRelation userModelRelation = new UserModelRelation();
        userModelRelation.setUserId(userId);
        return userModelRelation;
    }

    private OperateLog constructNewOperateLog(long userId, long bankId){
        OperateLog operateLog = new OperateLog();
        operateLog.setUserId(userId);
        operateLog.setBankId(bankId);
        return operateLog;
    }

    private OperateLog constructNewOperateLog(long userId, BioModelInfoDTO bioModelInfoDTO){
        OperateLog operateLog = new OperateLog();
        operateLog.setUserId(userId);
        operateLog.setBankId(bioModelInfoDTO.getBankId());
        operateLog.setModelId(bioModelInfoDTO.getModelId());
        return operateLog;
    }

    private UserModelRelation constructNewUserModelRelation(long modifiedUserId, long modelId, int newUMPriority) {
        UserModelRelation userModelRelation = new UserModelRelation();
        userModelRelation.setUserId(modifiedUserId);
        userModelRelation.setModelId(modelId);
        userModelRelation.setUmPriority(newUMPriority);
        return userModelRelation;
    }

    private boolean validateModelBankModifyPriority(long userId, long bankId){
        UserBankRelationDTO userBankRelationDTO = ubRelationDao.getUserBankRelationByUserIdAndBankId(userId,bankId);
        int operatePriority = userBankRelationDTO.getUbPriority();
        return (operatePriority>>1)%2 == 0;
    }

    private boolean validateModelBankSearchPriority(long userId, long bankId){
        UserBankRelationDTO userBankRelationDTO = ubRelationDao.getUserBankRelationByUserIdAndBankId(userId,bankId);
        int operatePriority = userBankRelationDTO.getUbPriority();
        return operatePriority%2 == 0;
    }

    private boolean validateBioModelModifyPriority(long userId, long modelId){
        UserModelRelationDTO userModelRelationDTO = umRelationDao.getUserModelRelationByUserIdAndModelId(userId,modelId);
        int operatePriority = userModelRelationDTO.getUmPriority();
        return (operatePriority>>1)%2 == 0;
    }

    private boolean validateBioModelSearchPriority(long userId, long modelId){
        UserModelRelationDTO userModelRelationDTO = umRelationDao.getUserModelRelationByUserIdAndModelId(userId,modelId);
        int operatePriority = userModelRelationDTO.getUmPriority();
        return operatePriority%2 == 0;
    }
}
