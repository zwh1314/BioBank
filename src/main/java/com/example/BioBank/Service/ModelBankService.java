package com.example.BioBank.Service;

import com.example.BioBank.DTO.ModelBankDTO;
import com.example.BioBank.Entity.ModelBankSQLConditions;
import com.example.BioBank.Response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModelBankService {

    /**新建生物样品库
     * @param userId
     * @param bankCapacity
     * @param bankName
     * @return 成功返回true，失败返回false
     */
    Response<Boolean> createModelBank(long userId, int bankCapacity, String bankName);

    /**获得一周内被修改过的生物样品库
     * @return 成功返回true，失败返回false
     */
    Response<List<ModelBankDTO>> getModelBankModifiedInOneWeek();

    /**更新生物样品库
     * @param userId
     * @param modelBankSQLConditions
     * @return 成功返回true，失败返回false
     */
    Response<Boolean> updateModelBank(long userId, ModelBankSQLConditions modelBankSQLConditions);

    /**更新用户-样品库操作权限
     * @param userId
     * @param modifiedUserId
     * @param bankId
     * @param newUBPriority
     * @return 成功返回true，失败返回false
     */
    Response<Boolean> updateUserBankRelation(long userId, long modifiedUserId, long bankId, int newUBPriority);

    /**
     * 通过生物样品库查询条件集查询样品库
     * @param userId
     * @param modelBankSQLConditions
     * @return 查询结果链表
     */
    Response<List<ModelBankDTO>> getModelBankByConditions(long userId, ModelBankSQLConditions modelBankSQLConditions);

    /**
     * 判断生物样品库是否已满
     * @param bankId
     * @return 已满返回true，未满返回false
     */
    Response<Boolean> getModelBankIsFullOrNot(long bankId);

    /**删除活动
     * @param userId
     * @param bankId
     * @return 成功返回true，失败返回false
     */
    Response<Boolean> deleteModelBankByBankId(long userId, long bankId);
}
