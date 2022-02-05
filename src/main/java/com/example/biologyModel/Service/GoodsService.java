package com.example.biologyModel.Service;

import com.example.biologyModel.DTO.GoodsDTO;
import com.example.biologyModel.Response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoodsService {
    Response<List<GoodsDTO>> GetALlGoods();

    Response<Boolean>updateGoodsNum(long goodsId);

    Response<Boolean>BuyGoods(long goodsId,long userId);

    Response<GoodsDTO>getGoodsInfoById(long goodsId);
}
