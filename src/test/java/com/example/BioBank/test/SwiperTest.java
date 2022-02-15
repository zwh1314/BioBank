package com.example.BioBank.test;

import com.example.BioBank.Entity.Swiper;
import com.example.BioBank.Service.SwiperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwiperTest {

    @Autowired
    private SwiperService swiperService;

    @Test
    public void swiperTest(){
        Swiper swiper=new Swiper();
        swiper.setNewsId(1);
        swiper.setSwiperPicture("http:\\127.0.0.1:8080\\test");
        swiper.setSwiperText("拨开云雾见太阳");
        swiper.setSwiperPriority("第一优先级");
        //swiperService.addSwiper(swiper);

        System.out.println(swiperService.getSwiperByNewsId(1));
        System.out.println(swiperService.getSwiperByPriority("第一优先级"));

        swiperService.updateSwiperText("太阳",1);
        swiperService.updateSwiperPriority("第二优先级",1);

        swiperService.deleteSwiperById(1);
    }
}
