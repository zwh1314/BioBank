package com.example.BioBank.test;

import com.example.BioBank.Dao.ModelBankDao;
import com.example.BioBank.Entity.ModelBank;
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
public class ModelBankTest {
    @Autowired
    private ModelBankDao modelBankDao;

    @Test
    public void firstTest() throws ParseException {

        ModelBank modelBank1 =new ModelBank();

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateformat.parse("2020-12-25 00:00:00");

        modelBankDao.insertModelBank(modelBank1);

    }
    @Test
    public void deleteTest(){

    }
}
