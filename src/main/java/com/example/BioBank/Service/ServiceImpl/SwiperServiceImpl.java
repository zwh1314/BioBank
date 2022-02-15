package com.example.BioBank.Service.ServiceImpl;

import com.example.BioBank.Dao.SwiperDao;
import com.example.BioBank.Entity.Swiper;
import com.example.BioBank.Response.Response;
import com.example.BioBank.Service.SwiperService;
import com.example.BioBank.enums.ResponseEnum;
import com.example.BioBank.utils.OSSUtil;
import com.example.BioBank.utils.SerialUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SwiperServiceImpl implements SwiperService {
    private static final Logger logger = LoggerFactory.getLogger(SwiperServiceImpl.class);

    @Autowired
    private SwiperDao swiperDao;

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
        Swiper swiper=new Swiper();
        swiper.setSwiperText("");
        swiper.setNewsId(newsId);
        swiper.setSwiperPriority("");
        swiper.setSwiperPicture(url);

        boolean result = swiperDao.addSwiper(swiper) > 0;
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
        boolean result = swiperDao.updateSwiperPriority(newSwiperPriority,swiperId) > 0;
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
        boolean result = swiperDao.updateSwiperText(newSwiperText,swiperId) > 0;
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
    public Response<List<Swiper>> getSwiperByNewsId(long newsId){
        Response<List<Swiper>> response=new Response<>();

        List<Swiper> swiperList = swiperDao.findSwiperByNews(newsId);
        if (swiperList.size() == 0) {
            response.setFail(ResponseEnum.SWIPER_NEWS_NOT_FOUND);
        }
        else {
            response.setSuc(swiperList);
        }
        return response;
    }

    @Override
    public Response<List<Swiper>> getSwiperByPriority(String priority){
        Response<List<Swiper>> response=new Response<>();

        List<Swiper> swiperList = swiperDao.findSwiperByPriority(priority);
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

        boolean result=swiperDao.deleteSwiperById(swiperId) > 0;
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
    public Response<List<Swiper>> getSwiperByNumber(long number){
        Response<List<Swiper>> response=new Response<>();
        List<Swiper> swiperList = swiperDao.findSwiperByNumber(number);
        if (swiperList.size() == 0) {
            response.setFail(ResponseEnum.SWIPER_NEWS_NOT_FOUND);
        }
        else {
            response.setSuc(swiperList);
        }
        return response;
    }
}
