package com.example.volunteer.test;
//找到文件打印到哪里了
import com.example.volunteer.Controller.ActivityNewsController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogTest {
    @Test
    public void logTest(){
        Logger logger = LoggerFactory.getLogger(LogTest.class);
        logger.error("error!");
        logger.info("info!");
        logger.trace("trace!");
        logger.warn("warn!");
        logger.debug("debug!");
    }




}
