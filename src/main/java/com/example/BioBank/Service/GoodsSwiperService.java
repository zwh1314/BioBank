package com.example.BioBank.Service;

import com.example.BioBank.Entity.GoodsSwiper;
import com.example.BioBank.Response.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface GoodsSwiperService {
    Response<Boolean> addSwiper(long newsId, MultipartFile swiper_picture);

    Response<Boolean> updateSwiperPriority(String newSwiperPriority, long swiperId);

    Response<Boolean> updateSwiperText(String newSwiperText, long swiperId);

    Response<List<GoodsSwiper>> getSwiperByNewsId(long newsId);

    Response<List<GoodsSwiper>> getSwiperByPriority(String priority);

    Response<Boolean> deleteSwiperById(long swiperId);

    Response<List<GoodsSwiper>> getSwiperByNumber(long number);
}
