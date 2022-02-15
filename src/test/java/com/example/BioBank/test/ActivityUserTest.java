package com.example.BioBank.test;

import com.example.BioBank.Dao.ActivityUserDao;
import com.example.BioBank.Entity.ActivityUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityUserTest  {
    @Autowired
    private ActivityUserDao activityUserDao;

    @Test
    public void firstest() throws ParseException {
        ActivityUser activityUser = new ActivityUser();
        activityUser.setUserId(1);
        activityUser.setActivityId(2);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateformat.parse("2020-12-25 00:00:00");
        activityUser.setFormDate(date);
        activityUser.setFormStatus(true);
        activityUserDao.addActivityUser(activityUser);
        List<ActivityUser> list1 = activityUserDao.findActivityUserByActivityId(activityUser.getActivityId());
        List<ActivityUser> list2 = activityUserDao.findActivityUserByUserId(activityUser.getUserId());
        System.out.println(list1);
        System.out.println(list2);


    }
    @Test
    public void deleteTest(){
        activityUserDao.deleteActivityUserByActivityId(2);
    }



}
