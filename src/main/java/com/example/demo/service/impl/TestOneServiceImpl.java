package com.example.demo.service.impl;

import com.example.demo.service.TestService;

public class TestOneServiceImpl implements TestService {
    @Override
    public String oneTest() {
        System.out.println("TestOneServiceImpl");
        return null;
    }

}
