package com.example.BioBank.test;

import com.example.BioBank.utils.EmailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {
    @Test
   public void test1(){
       EmailUtil emailUtil = new EmailUtil();
       String str = "1803697047@qq.com";
       String code = emailUtil.sendMail(str);
       System.out.println(code);
   }

}
