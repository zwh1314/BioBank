package com.example.BioBank.Service.ServiceImpl;

import com.example.BioBank.DTO.UserBankRelationDTO;
import com.example.BioBank.DTO.UserDTO;
import com.example.BioBank.Dao.*;
import com.example.BioBank.DTO.ModelBankDTO;
import com.example.BioBank.Entity.*;
import com.example.BioBank.Exception.BioBankRuntimeException;
import com.example.BioBank.Response.Response;
import com.example.BioBank.enums.OperatePriorityEnum;
import com.example.BioBank.enums.OperateTypeEnum;
import com.example.BioBank.enums.ResponseEnum;

import com.example.BioBank.Service.ModelBankService;
import com.example.BioBank.enums.UserPriorityEnum;
import com.example.BioBank.utils.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModelBankImpl implements ModelBankService {
    private static final Logger logger = LoggerFactory.getLogger(ModelBankImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelBankDao modelBankDao;

    @Autowired
    private BioModelDao bioModelDao;

    @Autowired
    private UBRelationDao ubRelationDao;

    @Autowired
    private OperateLogDao operateLogDao;

    @Autowired
    private BaseOperateLogDao baseOperateLogDao;

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> createModelBank(long userId, int bankCapacity, String bankName) {
        Response<Boolean> response = new Response<>();

        try {
            ModelBank modelBank = constructNewModelBank(bankCapacity, bankName);
            modelBank.setBankAccessible(OperatePriorityEnum.MODEL_BANK_ACCESSIBLE.getValue());
            UserBankRelation userBankRelation = constructNewUBRelation(userId);
            userBankRelation.setUbPriority(OperatePriorityEnum.UB_RELATION_PRIORITY_7.getValue());
            OperateLog operateLog = constructNewOperateLog(userId);
            operateLog.setOperateType(OperateTypeEnum.ADD.getCode());
            //新建生物样品库
            modelBankDao.insertModelBank(modelBank);
            //更新bankId
            userBankRelation.setBankId(modelBank.getBankId());
            operateLog.setBankId(modelBank.getBankId());
            //新建用户-样品库权限关系
            ubRelationDao.insertUserBankRelation(userBankRelation);
            //插入新建生物样品库日志记录
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e) {
            logger.warn("[createModelBank Operate DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> updateModelBank(long userId, ModelBankSQLConditions modelBankSQLConditions) {
        Response<Boolean> response = new Response<>();
        //查询用户更新权限
        UserBankRelationDTO userBankRelationDTO = ubRelationDao.getUserBankRelationByUserIdAndBankId(userId, modelBankSQLConditions.getBankId());
        int operatePriority = userBankRelationDTO.getUbPriority();
        if ((operatePriority >> 1) % 2 == 0) {
            logger.warn("[User's Priority Is Not Enough], User's Priority: {}", OperatePriorityEnum.getPriorityMsgByPriority(operatePriority));
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        try {
            //更新样本库属性
            OperateLog operateLog = constructNewOperateLog(userId);
            operateLog.setBankId(modelBankSQLConditions.getBankId());
            operateLog.setOperateType(OperateTypeEnum.MODIFY.getCode());
            //更新生物样品库
            modelBankDao.updateConditions(modelBankSQLConditions);
            //插入改动生物样品库日志记录
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e) {
            logger.warn("[updateModelBank Modify DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> updateUserBankRelation(long userId, long modifiedUserId, long bankId, int newUBPriority) {
        Response<Boolean> response = new Response<>();
        //查询用户更新权限
        UserBankRelationDTO userBankRelationDTO = ubRelationDao.getUserBankRelationByUserIdAndBankId(userId, bankId);
        int operatePriority = userBankRelationDTO.getUbPriority();
        if (operatePriority < OperatePriorityEnum.UB_RELATION_PRIORITY_7.getValue()) {
            logger.warn("[User's Priority Is Not Enough], User's Priority: {}", OperatePriorityEnum.getPriorityMsgByPriority(operatePriority));
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }
        if (newUBPriority > OperatePriorityEnum.UB_RELATION_PRIORITY_3.getValue()) {
            logger.warn("[Operate-Remove Priority Only Belongs To The Owner]");
            response.setFail(ResponseEnum.OPERATE_REMOVE_PRIORITY_ONLY_BELONGS_TO_THE_OWNER);
            return response;
        }
        UserBankRelationDTO modifiedUserBankRelationDTO = ubRelationDao.getUserBankRelationByUserIdAndBankId(modifiedUserId, bankId);
        try {
            BaseOperateLog baseOperateLog = new BaseOperateLog();
            baseOperateLog.setOperateDescription("用户: " + userId + ", 将用户: " + modifiedUserId + " 对生物样品库: " + bankId + ", 的操作权限更新为: " + OperatePriorityEnum.getPriorityMsgByPriority(newUBPriority));
            if (modifiedUserBankRelationDTO == null) {
                //插入用户-样本库操作权限
                UserBankRelation userBankRelation = constructNewUserBankRelation(modifiedUserId, bankId, newUBPriority);
                //更新生物样品库
                ubRelationDao.insertUserBankRelation(userBankRelation);
                //插入基本操作日志记录
                baseOperateLogDao.insertBaseOperateLog(baseOperateLog);
            } else {
                //更新用户-样本库操作权限
                //更新生物样品库
                ubRelationDao.updateUBPriority(modifiedUserId, bankId, newUBPriority);
                //插入基本操作日志记录
                baseOperateLogDao.insertBaseOperateLog(baseOperateLog);
            }
        } catch (Exception e) {
            logger.warn("[updateUserBankRelation Modify DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    @Override
    public Response<List<ModelBankDTO>> getModelBankByConditions(long userId, ModelBankSQLConditions modelBankSQLConditions) {
        Response<List<ModelBankDTO>> response = new Response<>();
        List<ModelBankDTO> modelBankDTOList;
        //按条件集查找生物样品库
        modelBankDTOList = modelBankDao.getModelBankByConditions(modelBankSQLConditions);
        if (modelBankDTOList == null || modelBankDTOList.size() == 0) {
            logger.warn("[Search DataBase By Conditions Fail], Conditions: {}", SerialUtil.toJsonStr(modelBankSQLConditions));
            response.setFail(ResponseEnum.OBJECT_RELATED_NOT_FOUND);
        } else {
            response.setSuc(modelBankDTOList);
        }
        return response;
    }

    @Override
    public Response<List<ModelBankDTO>> getModelBankModifiedInOneWeek() {
        Response<List<ModelBankDTO>> response = new Response<>();

        List<ModelBankDTO> modelBankDTOList = modelBankDao.findModelBankModifiedInOneWeek();
        if (modelBankDTOList.isEmpty()) {
            logger.warn("[ModelBanks Modified In One Week Are Not Found]");
            response.setFail(ResponseEnum.OBJECT_IN_ONE_WEEK_NOT_FOUND);
        } else {
            response.setSuc(modelBankDTOList);
        }
        return response;
    }

    @Override
    public Response<Boolean> getModelBankIsFullOrNot(long bankId){
        Response<Boolean> response = new Response<>();

        /**todo 用redis分布式锁去做*/
        int bioModelNum = bioModelDao.findBioModelNumInTheBank(bankId);
        ModelBankSQLConditions modelBankSQLConditions = new ModelBankSQLConditions();
        modelBankSQLConditions.setBankId(bankId);
        ModelBankDTO modelBankDTO = modelBankDao.getModelBankByConditions(modelBankSQLConditions).get(0);

        if(bioModelNum == modelBankDTO.getBankCapacity()){
            response.setSuc(true);
        }else {
            response.setSuc(false);
        }
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> deleteModelBankByBankId(long userId, long bankId) {
        Response<Boolean> response = new Response<>();

        //查询用户删除权限
        UserBankRelationDTO userBankRelationDTO = ubRelationDao.getUserBankRelationByUserIdAndBankId(userId, bankId);
        UserDTO userDTO = userDao.getUserByUserId(userId);
        int operatePriority = userBankRelationDTO.getUbPriority();
        if ((operatePriority >> 2) % 2 == 0 && userDTO.getPriority() < UserPriorityEnum.SUPERADMINISTRATOR.getPriority()) {
            logger.warn("[User's Priority Is Not Enough], User's Priority: {}", OperatePriorityEnum.getPriorityMsgByPriority(operatePriority));
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        try {
            OperateLog operateLog = constructNewOperateLog(userId);
            operateLog.setBankId(bankId);
            operateLog.setOperateType(OperateTypeEnum.REMOVE.getCode());
            //删除样本库
            modelBankDao.deleteModelBankByBankId(bankId);
            //插入删除生物样品库日志记录
            operateLogDao.insertOperateLog(operateLog);
        } catch (Exception e) {
            logger.warn("[deleteModelBankByBankId Delete DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        response.setSuc(true);
        return response;
    }

    private ModelBank constructNewModelBank(int bankCapacity, String bankName) {
        ModelBank modelBank = new ModelBank();
        modelBank.setBankCapacity(bankCapacity);
        modelBank.setBankName(bankName);
        return modelBank;
    }

    private UserBankRelation constructNewUBRelation(long userId) {
        UserBankRelation userBankRelation = new UserBankRelation();
        userBankRelation.setUserId(userId);
        return userBankRelation;
    }

    private OperateLog constructNewOperateLog(long userId) {
        OperateLog operateLog = new OperateLog();
        operateLog.setUserId(userId);
        return operateLog;
    }

    private UserBankRelation constructNewUserBankRelation(long modifiedUserId, long bankId, int newUBPriority) {
        UserBankRelation userBankRelation = new UserBankRelation();
        userBankRelation.setUserId(modifiedUserId);
        userBankRelation.setBankId(bankId);
        userBankRelation.setUbPriority(newUBPriority);
        return userBankRelation;
    }
}
