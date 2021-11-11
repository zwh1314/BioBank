package com.example.volunteer.test;

import com.example.volunteer.Entity.ActivityNews;
import com.example.volunteer.Request.ActivityNewsRequest;
import com.example.volunteer.Service.ActivityNewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityNewsTest {

    @Autowired
    private ActivityNewsService activityNewsService;

    @Test
    public void activityNewsTest(){
        ActivityNewsRequest activityNewsRequest=new ActivityNewsRequest();
        List<ActivityNews> activityNewsList=new ArrayList<>();
        ActivityNews activityNews=new ActivityNews();
        activityNews.setActivityId(1);
        activityNews.setNewsContent("测试1");
        activityNews.setNewsTitle("测试");
        //activityNews.setNewsPicture("http:\\127.0.0.1:8080\\test");
        activityNews.setNewsPublisher(1L);
        activityNewsList.add(activityNews);
        ActivityNews activityNews1=new ActivityNews();
        activityNews1.setActivityId(1);
        activityNews1.setNewsContent("测试2");
        activityNews1.setNewsTitle("测试");
        //activityNews1.setNewsPicture("http:\\127.0.0.1:8080\\test");
        activityNews1.setNewsPublisher(2L);
        activityNewsList.add(activityNews1);
        activityNewsRequest.setActivityNewsList(activityNewsList);

        //activityNewsService.addActivityNews(activityNewsRequest);
        System.out.println(activityNewsService.getActivityNewsByActivityId(1));
        System.out.println(activityNewsService.getActivityNewsByPublisherId(1));
        System.out.println(activityNewsService.getActivityNewsInOneWeek());
        System.out.println(activityNewsService.getActivityNewsByRelativeText(""));

        //activityNewsService.updateActivityNewsContent("猪猪侠",1);
        //activityNewsService.updateActivityNewsPicture("http:\\127.0.0.1:8080\\test1",1);
        activityNewsService.updateActivityNewsTitle("更新测试",1);

        activityNewsService.deleteActivityNewsById(1L);
    }
}
