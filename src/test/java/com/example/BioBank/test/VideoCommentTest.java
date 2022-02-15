package com.example.BioBank.test;

import com.example.BioBank.Entity.VideoComment;
import com.example.BioBank.Request.VideoCommentRequest;
import com.example.BioBank.Service.VideoCommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoCommentTest {

    @Autowired
    private VideoCommentService videoCommentService;

    @Test
    public void videoCommentTest(){
        VideoCommentRequest videoCommentRequest=new VideoCommentRequest();
        List<VideoComment> videoCommentList=new ArrayList<>();
        VideoComment videoComment=new VideoComment();
        videoComment.setCommentText("张文瀚测试");
        videoComment.setCommentPublisher(1L);
        videoComment.setCommentLike(6666);
        videoCommentList.add(videoComment);
        VideoComment videoComment1=new VideoComment();
        videoComment1.setCommentText("张文瀚测试2");
        videoComment1.setCommentPublisher(2L);
        videoComment1.setCommentLike(6666);
        videoCommentList.add(videoComment1);
        videoCommentRequest.setVideoCommentList(videoCommentList);

        videoCommentService.addVideoComment(videoCommentRequest);
        System.out.println(videoCommentService.getVideoCommentByPublisher(1L));
        System.out.println(videoCommentService.getVideoCommentByRelativeText("张文瀚"));
        System.out.println(videoCommentService.getVideoCommentInOneWeek());

        videoCommentService.updateVideoCommentText("猪猪侠",1L);
        videoCommentService.updateVideoCommentLikeNumber(1L);

        videoCommentService.deleteVideoCommentById(2L);
    }
}
