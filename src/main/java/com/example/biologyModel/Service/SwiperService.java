package com.example.biologyModel.Service;

import com.example.biologyModel.Entity.Swiper;
import com.example.biologyModel.Response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SwiperService {
    Response<Boolean> addSwiper(long newsId, MultipartFile swiper_picture);

    Response<Boolean> updateSwiperPriority(String newSwiperPriority, long swiperId);

    Response<Boolean> updateSwiperText(String newSwiperText, long swiperId);

    Response<List<Swiper>> getSwiperByNewsId(long newsId);

    Response<List<Swiper>> getSwiperByPriority(String priority);

    Response<Boolean> deleteSwiperById(long swiperId);

    Response<List<Swiper>> getSwiperByNumber(long number);
}
