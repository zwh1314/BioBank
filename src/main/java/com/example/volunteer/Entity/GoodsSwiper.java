package com.example.volunteer.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "goods_swiper")
public class GoodsSwiper implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "swiper_id")
    @ApiModelProperty(value = "轮播图id")
    private long swiperId;

    @Column(name = "news_id")
    @ApiModelProperty(value = "轮播图对应新闻id")
    private long newsId;

    @Column(name = "swiper_picture")
    @ApiModelProperty(value = "轮播图图片")
    private String swiperPicture;

    @Column(name = "swiper_text")
    @ApiModelProperty(value = "轮播图文本")
    private String swiperText;

    @Column(name = "swiper_priority")
    @ApiModelProperty(value = "轮播图优先级")
    private String swiperPriority;
}
