package com.example.demo.factory;

import com.example.demo.enums.TestEnum;
import com.example.demo.service.TestService;

public class TestFactory {

    public static TestService getTestService(String mark){
        try {
            String path = TestEnum.getPathByMark(mark);
            return (TestService)Class.forName(path).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
