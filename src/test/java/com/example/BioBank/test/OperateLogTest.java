package com.example.BioBank.test;

import com.example.BioBank.RedisService.OperateLogRedisService;
import com.example.BioBank.RedisService.RedisServiceImpl.OperateLogRedisServiceImpl;
import com.example.BioBank.Service.OperateLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperateLogTest {

    @Autowired
    private OperateLogService operateLogService;

    @Test
    public void commentTest(){
        OperateLogRedisService operateLogRedisService = new OperateLogRedisServiceImpl();
        operateLogRedisService.getCommentLikeByCommentId(1L);

  /*      CommentRequest commentRequest=new CommentRequest();
        List<Comment> commentList=new ArrayList<>();
        Comment comment=new Comment();
        comment.setCommentText("张文瀚测试");
        comment.setCommentPublisher(1L);
        comment.setCommentLike(6666);
        commentList.add(comment);
        Comment comment1=new Comment();
        comment1.setCommentText("张文瀚测试2");
        comment1.setCommentPublisher(2L);
        comment1.setCommentLike(6666);
        commentList.add(comment1);
        commentRequest.setCommentList(commentList);

        commentService.addComment(commentRequest);
        System.out.println(commentService.getCommentByPublisher(1L));
        System.out.println(commentService.getCommentByRelativeText("张文瀚"));
        System.out.println(commentService.getCommentInOneWeek());

        commentService.updateCommentText("猪猪侠",1L);
        commentService.updateCommentLikeNumber(10000,1L);

        commentService.deleteCommentById(2L);*/
    }
}
