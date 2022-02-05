package com.example.biologyModel.test;

import com.example.biologyModel.DTO.UserDTO;
import com.example.biologyModel.Dao.UserDao;
import com.example.biologyModel.Entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void firstTest() {

        User user1=new User();
        //user1.setUserName("张文瀚");
        user1.setPassword("991219");
        user1.setTel("15082361803");
        user1.setPriority("管理员");
        userDao.insertUser(user1);
        UserDTO userDTO=userDao.getUserByTel(user1.getTel());
        System.out.println(userDao.getUserByTel(user1.getTel()));

//        user1.setPassword("1234");
//        userDao.updatePassword(user1.getTel(),user1.getPassword());
//        System.out.println(userDao.getUserByTel(user1.getTel()));
    }
    @Test
    public void deleteTest(){
        userDao.deleteByUserId(4L);
    }
}
