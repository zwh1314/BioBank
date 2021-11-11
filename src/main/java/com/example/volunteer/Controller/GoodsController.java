package com.example.volunteer.Controller;

import com.example.volunteer.DTO.GoodsDTO;
import com.example.volunteer.Exception.VolunteerRuntimeException;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.GoodsService;
import com.example.volunteer.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "商品Controller")
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    GoodsService goodsService;

    @GetMapping("/getGoods")
    @ApiOperation("获得goodsInfo")
    public Response<List<GoodsDTO>> getGoods() {
        Response<List<GoodsDTO>> response = new Response<>();
        try {
            return goodsService.GetALlGoods();
        } catch (IllegalArgumentException e) {
            logger.warn("[GetALlGoods Illegal Argument]",  e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[GetALlGoods Runtime Exception]",e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[GetALlGoods Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @GetMapping("/getGoodsInfoById")
    @ApiOperation("获得goodsInfoById")
    public Response<GoodsDTO> getGoodsInfoById(@Param("goodsId")long goodsId) {
        Response<GoodsDTO> response = new Response<>();
        try {
            return goodsService.getGoodsInfoById(goodsId);
        } catch (IllegalArgumentException e) {
            logger.warn("[GetALlGoods Illegal Argument]",  e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[GetALlGoods Runtime Exception]",e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[GetALlGoods Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/buyGoods")
    @ApiOperation("获得goods")
    public Response<Boolean> BuyGoods(@Param("goodsId") long goodsId) {
        Response<Boolean> response = new Response<>();
        try {
            long userId = getUserId();
            return goodsService.BuyGoods(goodsId,userId);
        } catch (IllegalArgumentException e) {
            logger.warn("[GetALlGoods Illegal Argument]",  e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[GetALlGoods Runtime Exception]",e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[GetALlGoods Exception]", e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


}
