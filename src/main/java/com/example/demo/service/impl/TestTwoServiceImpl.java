package com.example.demo.service.impl;

import com.example.demo.service.TestService;

public class TestTwoServiceImpl implements TestService {
    @Override
    public String oneTest() {
        System.out.println("TestTwoServiceImpl");
        return null;
    }
}
