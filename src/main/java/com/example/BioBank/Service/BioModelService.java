package com.example.BioBank.Service;

import com.example.BioBank.DTO.BioModelDTO;
import com.example.BioBank.DTO.BioModelInfoDTO;
import com.example.BioBank.Entity.BioModel;
import com.example.BioBank.Entity.BioModelSQLConditions;
import com.example.BioBank.Response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BioModelService {
    Response<Boolean> storeNewBioModel(long userId, BioModel bioModel);

    Response<BioModelInfoDTO> getBioModelInfoByModelId(long userId, long modelId);

    Response<List<BioModelDTO>>getBioModelByConditions(long userId, BioModelSQLConditions bioModelSQLConditions);

    Response<List<BioModelDTO>>getBioModelModifiedInOneWeek(long userId, long bankId);

    Response<List<BioModelDTO>>getBioModelInsertInOneWeek(long userId, long bankId);

    Response<List<BioModelDTO>>getBioModelByTemperatureLimit(long userId, long bankId, float modelTemperatureMost, float modelTemperatureLeast);

    Response<Boolean> updateBioModel(long userId, BioModelSQLConditions bioModelSQLConditions);

    Response<Boolean> updateBioModelTemperatureLimit(long userId, long bankId, long modelId, float newModelTemperatureMost, float newModelTemperatureLeast);

    Response<Boolean> updateUserModelRelation(long userId, long modifiedUserId, long modelId, int newUMPriority);

    Response<Boolean> deleteBioModelByModelId(long userId, long bankId, long modelId);
}
