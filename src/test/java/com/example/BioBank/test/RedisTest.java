package com.example.BioBank.test;

import com.example.BioBank.Entity.User;
import com.example.BioBank.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void redisTest(){
        User user=new User();
        user.setUserId(1);
        //user.setUserName("张文瀚");
        user.setPassword("991219");
        user.setPriority(2);
        user.setTel("15082361803");
        redisUtil.set("zwh",user);
        System.out.println(redisUtil.get("zwh"));
    }
}
