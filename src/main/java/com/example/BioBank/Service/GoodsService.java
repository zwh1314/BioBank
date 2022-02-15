package com.example.BioBank.Service;

import com.example.BioBank.DTO.GoodsDTO;
import com.example.BioBank.Response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoodsService {
    Response<List<GoodsDTO>> GetALlGoods();

    Response<Boolean>updateGoodsNum(long goodsId);

    Response<Boolean>BuyGoods(long goodsId,long userId);

    Response<GoodsDTO>getGoodsInfoById(long goodsId);
}
