package com.example.demo;

import com.example.demo.factory.TestFactory;
import com.example.demo.service.TestService;

public class ReflectTest {

    public static void main(String[] args) {
        TestService t1 = TestFactory.getTestService("1");
        TestService t2 = TestFactory.getTestService("2");
        t1.oneTest();
        t2.oneTest();
    }

}
