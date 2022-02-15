package com.example.BioBank.test;

import com.example.BioBank.Service.CommentResponseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentResponseTest {

    @Autowired
    private CommentResponseService commentResponseService;

    @Test
    public void commentResponseTest(){
//        CommentResponseRequest commentResponseRequest=new CommentResponseRequest();
//        List<CommentResponse> commentResponseList=new ArrayList<>();
//        CommentResponse commentResponse=new CommentResponse();
//        commentResponse.setCommentId(1L);
//        commentResponse.setResponseText("评论回复测试1");
//        commentResponse.setResponsePublisher(1L);
//        commentResponse.setResponseType(0);
//        commentResponse.setResponseLike(10000);
//        commentResponseList.add(commentResponse);
//        CommentResponse commentResponse1=new CommentResponse();
//        commentResponse1.setCommentId(2L);
//        commentResponse1.setResponseText("评论回复测试2");
//        commentResponse1.setResponsePublisher(1L);
//        commentResponse1.setResponseType(1);
//        commentResponse1.setResponseLike(666666);
//        commentResponseList.add(commentResponse1);
//        commentResponseRequest.setCommentResponseList(commentResponseList);
//
//        commentResponseService.addCommentResponse(commentResponseRequest);
        System.out.println(commentResponseService.getCommentResponseByCommentId(1));
        System.out.println(commentResponseService.getVideoCommentResponseByCommentId(2L));
//        System.out.println(commentResponseService.getCommentResponseByPublisherId(1L));
//        System.out.println(commentResponseService.getVideoCommentResponseByPublisherId(1L));
//        System.out.println(commentResponseService.getCommentResponseByRelativeText("评论"));
//        System.out.println(commentResponseService.getVideoCommentResponseByRelativeText("评论") );
//        System.out.println(commentResponseService.getCommentResponseInOneWeek());
//        System.out.println(commentResponseService.getVideoCommentResponseInOneWeek());

//        commentResponseService.updateResponseText("猪猪侠",3L);
//        commentResponseService.updateResponseLikeNumber(1314,3L);
//
//        commentResponseService.deleteCommentResponseById(1L);
    }
}
