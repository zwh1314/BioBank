package com.example.volunteer.Service.ServiceImpl;

import com.example.volunteer.DTO.GoodsDTO;
import com.example.volunteer.DTO.UserInfoDTO;
import com.example.volunteer.Dao.GoodsDao;
import com.example.volunteer.Dao.UserInfoDao;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.GoodsService;
import com.example.volunteer.enums.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public Response<List<GoodsDTO>> GetALlGoods(){
        Response<List<GoodsDTO>> response=new Response<>();
        List<GoodsDTO> goodsDTOList;

        goodsDTOList = goodsDao.findAllGoods();
        if(CollectionUtils.isEmpty(goodsDTOList)){
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            return response;
        }
        else{
            response.setSuc(goodsDTOList);
        }
        return response;

    }
    @Override
    public Response<Boolean> updateGoodsNum(long goodsId) {
        Response<Boolean> response = new Response<>();
        long num = -1;
        num = goodsDao.getGoodsNumByGoodsId(goodsId);
        if (num < 0) {
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
            return response;
        } else {
            num -= 1;
            int result = goodsDao.updateNum(goodsId,num);
            if(result > 0){
                response.setSuc(true);
            }else response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        return response;
    }
    @Override
    public Response<Boolean> BuyGoods(long goodsId,long userId){
        Response<Boolean> response = new Response<>();
        UserInfoDTO userInfoDTO =  userInfoDao.getCreditsById(userId);
        if(userInfoDTO != null){
            long credits = userInfoDTO.getCredits();
            GoodsDTO goodsDTO = goodsDao.getGoodsInfoByGoodsId(goodsId);
            long num = goodsDTO.getNum();
            if(num <= 0){
               response.setFail(ResponseEnum.NUM_NOT_ENOUGH);
            }else{
                if(credits >=goodsDTO.getValue()){
                    credits -= goodsDTO.getValue();
                    int result1 = userInfoDao.updateCredits(userId,credits);
                    int result2 = -1;
                    if(result1 > 0){
                        num -= 1;
                        result2 = goodsDao.updateNum(num,goodsId);
                    }
                    if(result1 > 0 && result2 > 0) response.setSuc(true);
                    else response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);

                }else {
                    response.setFail(ResponseEnum.INSUFFICIENT_CREDIT);
                }
            }
        }else {
            response.setFail(ResponseEnum.USER_NOT_FOUND);
        }
        return response;
    }
    @Override
    public Response<GoodsDTO>getGoodsInfoById(long goodsId){
        Response<GoodsDTO> response=new Response<>();
        GoodsDTO goodsDTO;

        goodsDTO = goodsDao.getGoodsInfoByGoodsId(goodsId);
        if(goodsDTO ==null){
            response.setFail(ResponseEnum.OBJECT_NOT_FOUND);
            return response;
        }
        else{
            response.setSuc(goodsDTO);
        }
        return response;
    }
}
