package com.example.biologyModel.test;

import com.example.biologyModel.Entity.Video;
import com.example.biologyModel.Request.VideoRequest;
import com.example.biologyModel.Service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoTest {

    @Autowired
    private VideoService videoService;

    @Test
    public void videoTest(){
        VideoRequest videoRequest=new VideoRequest();
        List<Video> videoList=new ArrayList<>();
        Video video=new Video();
        video.setVideoUrl("http:\\127.0.0.1:8080\\test");
        video.setVideoText("拨开云雾见太阳");
        video.setVideoPublisher(1);
        video.setVideoLike(10000);
        Video video1=new Video();
        video1.setVideoUrl("http:\\127.0.0.1:8080\\test1");
        video1.setVideoText("拨开云雾见太阳1");
        video1.setVideoPublisher(2);
        video1.setVideoLike(6666);
        videoList.add(video);
        videoList.add(video1);
        videoRequest.setVideoList(videoList);
//        videoService.addVideo(videoRequest);

        System.out.println(videoService.getVideoByPublisherId(1));
        System.out.println(videoService.getVideoByRelativeText("太阳"));
        System.out.println(videoService.getVideoInOneWeek());

        videoService.updateVideoLikeNumber(36666,1);
        videoService.updateVideoTextContent("猪猪侠",1);

        videoService.deleteVideoById(2);
    }
}
