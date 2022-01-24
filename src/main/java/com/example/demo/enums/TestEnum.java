package com.example.demo.enums;

public enum TestEnum {
    A("1","com.example.demo.service.impl.TestOneServiceImpl"),B("2","com.example.demo.service.impl.TestTwoServiceImpl");

    private String mark;
    private String path;

    private TestEnum(String mark, String path) {
        this.mark = mark;
        this.path = path;
    }

    public static String getPathByMark(String mark) {
        for (TestEnum testEnum : TestEnum.values())
            if (mark.equals(testEnum.getMark()))
                return testEnum.getPath();
        return null;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
