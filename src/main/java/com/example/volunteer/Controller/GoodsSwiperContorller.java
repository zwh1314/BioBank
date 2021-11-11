package com.example.volunteer.Controller;

import com.example.volunteer.Entity.GoodsSwiper;
import com.example.volunteer.Exception.VolunteerRuntimeException;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.GoodsSwiperService;
import com.example.volunteer.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "商品轮播图Controller")
@RestController
@RequestMapping("goodsSwiper")
public class GoodsSwiperContorller extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(GoodsSwiperContorller.class);

    @Autowired
    private GoodsSwiperService goodsSwiperService;

    @GetMapping("/getSwiperByPriority")
    @ApiOperation("获得轮播图by priority")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priority", value = "轮播图优先级", paramType = "query", dataType = "string"),
    })
    public Response<List<GoodsSwiper>> getSwiperByPriority(@RequestParam("priority") String priority) {
        Response<List<GoodsSwiper>> response = new Response<>();
        try {
            return goodsSwiperService.getSwiperByPriority(priority);
        } catch (IllegalArgumentException e) {
            logger.warn("[getSwiperByPriority Illegal Argument], priority: {}", priority, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getSwiperByPriority Runtime Exception], priority: {}", priority, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getSwiperByPriority Exception], priority: {}", priority, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }


    @PostMapping("/addSwiper")
    @ApiOperation("添加轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newsId", value = "新闻id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "swiper_picture", value = "轮播图图片", paramType = "query", dataType = "MultipartFile"),
    })
    public Response<Boolean> addSwiper(@RequestParam("newsId") long newsId, @RequestParam("swiper_picture") MultipartFile swiper_picture) {
        Response<Boolean> response = new Response<>();
        try {
            return goodsSwiperService.addSwiper(newsId,swiper_picture);
        } catch (IllegalArgumentException e) {
            logger.warn("[addSwiper Illegal Argument], swiper_picture: {}", swiper_picture.getOriginalFilename(), e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[addSwiper Runtime Exception], swiper_picture: {}", swiper_picture.getOriginalFilename(), e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[addSwiper Exception], swiper_picture: {}", swiper_picture.getOriginalFilename(), e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateSwiperPriority")
    @ApiOperation("更新轮播图优先级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newSwiperPriority", value = "轮播图优先级", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "swiperId", value = "轮播图id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> updateCommentLikeNumber(@RequestParam("newSwiperPriority")String newSwiperPriority,@RequestParam("swiperId") long swiperId) {
        Response<Boolean> response = new Response<>();
        try {
            return goodsSwiperService.updateSwiperPriority(newSwiperPriority,swiperId);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateSwiperPriority Illegal Argument], : swiperId {}", swiperId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateSwiperPriority Runtime Exception], : swiperId {}", swiperId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateSwiperPriority Exception], :swiperId {}", swiperId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/updateSwiperText")
    @ApiOperation("更新轮播图文本")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newSwiperText", value = "轮播图文本", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "swiperId", value = "轮播图id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> updateSwiperText(@RequestParam("newSwiperText")String newSwiperText,@RequestParam("swiperId") long swiperId) {
        Response<Boolean> response = new Response<>();
        try {
            return goodsSwiperService.updateSwiperText(newSwiperText,swiperId);
        } catch (IllegalArgumentException e) {
            logger.warn("[updateSwiperText Illegal Argument], : swiperId {}", swiperId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[updateSwiperText Runtime Exception], : swiperId {}", swiperId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[updateSwiperText Exception], :swiperId {}", swiperId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }



    @PostMapping("/deleteSwiperById")
    @ApiOperation("删除轮播图By swiperId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "swiperId", value = "评论id", paramType = "query", dataType = "long"),
    })
    public Response<Boolean> deleteSwiperById(@RequestParam("swiperId") long swiperId) {
        Response<Boolean> response = new Response<>();
        try {
            return goodsSwiperService.deleteSwiperById(swiperId);
        } catch (IllegalArgumentException e) {
            logger.warn("[deleteSwiperById Illegal Argument], swiperId: {}", swiperId, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[deleteSwiperById Runtime Exception], swiperId: {}", swiperId, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[deleteSwiperById Exception], swiperId: {}", swiperId, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
    @GetMapping("/getSwiperByNumber")
    @ApiOperation("获得轮播图by Number")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "需要轮播图个数", paramType = "query", dataType = "long"),
    })
    public Response<List<GoodsSwiper>> getSwiperByNumber(@RequestParam("number") long number) {
        Response<List<GoodsSwiper>> response = new Response<>();
        try {
            return goodsSwiperService.getSwiperByNumber(number);
        } catch (IllegalArgumentException e) {
            logger.warn("[getSwiperByNumber Illegal Argument], number: {}", number, e);
            response.setFail(ResponseEnum.ILLEGAL_PARAM);
            return response;
        } catch (VolunteerRuntimeException e) {
            logger.error("[getSwiperByNumber Runtime Exception], number: {}", number, e);
            response.setFail(e.getExceptionCode(), e.getMessage());
            return response;
        }  catch (Exception e) {
            logger.error("[getSwiperByNumber Exception], number: {}", number, e);
            response.setFail(ResponseEnum.SERVER_ERROR);
            return response;
        }
    }
}
