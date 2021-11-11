package com.example.volunteer.test;

import com.example.volunteer.DTO.ActivityDTO;
import com.example.volunteer.Dao.ActivityDao;
import com.example.volunteer.Entity.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityTest {
    @Autowired
    private ActivityDao activityDao;

    @Test
    public void firstTest() throws ParseException {

        Activity activity1=new Activity();
        activity1.setActivityName("活动1");
        activity1.setActivityContent("喂猫");
        activity1.setActivityOrganizer(1);
        activity1.setActivityType("normal");
        activity1.setEnrolledNumber(0);
        activity1.setRequestedNumber(50);
        activity1.setSignFileModel(false);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateformat.parse("2020-12-25 00:00:00");
        activity1.setActivityDate(date);
        activityDao.insertActivity(activity1);
        Activity activity=activityDao.getActivityByActivityId(activity1.getActivityId());
        System.out.println(activityDao.getActivityByActivityId(activity1.getActivityId()));


    }
    @Test
    public void deleteTest(){
        activityDao.deleteActivityByActivityId(1);
    }
}
