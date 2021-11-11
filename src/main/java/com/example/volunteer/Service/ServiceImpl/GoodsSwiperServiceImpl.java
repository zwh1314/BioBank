package com.example.volunteer.Service.ServiceImpl;

import com.example.volunteer.Dao.GoodsSwiperDao;
import com.example.volunteer.Entity.GoodsSwiper;
import com.example.volunteer.Response.Response;
import com.example.volunteer.Service.GoodsSwiperService;
import com.example.volunteer.enums.ResponseEnum;
import com.example.volunteer.utils.OSSUtil;
import com.example.volunteer.utils.SerialUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class GoodsSwiperServiceImpl implements GoodsSwiperService {
    private static final Logger logger = LoggerFactory.getLogger(GoodsSwiperServiceImpl.class);

    @Autowired
    private GoodsSwiperDao goodsSwiperDao;

    @Autowired
    private OSSUtil ossUtil;

    @Override
    public Response<Boolean> addSwiper(long newsId, MultipartFile swiper_picture){
        Response<Boolean> response=new Response<>();

        String bucketName = "swiper-picture";
        String filename = "news_"+newsId+"/"+swiper_picture.getOriginalFilename();
        String url = ossUtil.uploadFile(bucketName,swiper_picture,filename);
        if(StringUtils.isBlank(url)){
            logger.error("[uploadSwiper Fail], swiper_picture: {}", SerialUtil.toJsonStr(swiper_picture.getOriginalFilename()));
            response.setFail(ResponseEnum.UPLOAD_OSS_FAILURE);
            return response;
        }
        GoodsSwiper swiper=new GoodsSwiper();
        swiper.setSwiperText("");
        swiper.setNewsId(newsId);
        swiper.setSwiperPriority("");
        swiper.setSwiperPicture(url);

        boolean result = goodsSwiperDao.addSwiper(swiper) > 0;
        if (!result) {
            logger.error("[addSwiper Fail], swiper: {}", SerialUtil.toJsonStr(swiper));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else {
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Boolean> updateSwiperPriority(String newSwiperPriority, long swiperId){
        Response<Boolean> response=new Response<>();
        boolean result = goodsSwiperDao.updateSwiperPriority(newSwiperPriority,swiperId) > 0;
        if (!result) {
            logger.error("[updateSwiperPriority Fail], swiperId: {}", SerialUtil.toJsonStr(swiperId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else {
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<Boolean> updateSwiperText(String newSwiperText, long swiperId){
        Response<Boolean> response=new Response<>();
        boolean result = goodsSwiperDao.updateSwiperText(newSwiperText,swiperId) > 0;
        if (!result) {
            logger.error("[updateSwiperText Fail], swiperId: {}", SerialUtil.toJsonStr(swiperId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else {
            response.setSuc(true);
        }
        return response;
    }

    @Override
    public Response<List<GoodsSwiper>> getSwiperByNewsId(long newsId){
        Response<List<GoodsSwiper>> response=new Response<>();

        List<GoodsSwiper> swiperList = goodsSwiperDao.findSwiperByNews(newsId);
        if (swiperList.size() == 0) {
            response.setFail(ResponseEnum.SWIPER_NEWS_NOT_FOUND);
        }
        else {
            response.setSuc(swiperList);
        }
        return response;
    }

    @Override
    public Response<List<GoodsSwiper>> getSwiperByPriority(String priority){
        Response<List<GoodsSwiper>> response=new Response<>();

        List<GoodsSwiper> swiperList = goodsSwiperDao.findSwiperByPriority(priority);
        if (swiperList == null) {
            response.setFail(ResponseEnum.SWIPER_PRIORITY_NOT_FOUND);
        }
        else {
            response.setSuc(swiperList);
        }
        return response;
    }

    @Override
    public Response<Boolean> deleteSwiperById(long swiperId){
        Response<Boolean> response=new Response<>();

        boolean result=goodsSwiperDao.deleteSwiperById(swiperId) > 0;
        if(!result){
            logger.error("[deleteSwiperById Fail], swiperId: {}", SerialUtil.toJsonStr(swiperId));
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }
        else{
            response.setSuc(true);
        }
        return response;
    }
    @Override
    public Response<List<GoodsSwiper>> getSwiperByNumber(long number){
        Response<List<GoodsSwiper>> response=new Response<>();
        List<GoodsSwiper> swiperList = goodsSwiperDao.findSwiperByNumber(number);
        if (swiperList.size() == 0) {
            response.setFail(ResponseEnum.SWIPER_NEWS_NOT_FOUND);
        }
        else {
            response.setSuc(swiperList);
        }
        return response;
    }
}
