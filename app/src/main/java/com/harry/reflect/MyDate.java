/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.reflect;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/31.
 */
public class MyDate {

    private int day=31;
    private int month=10;
    private int year =2016;
    public void test(int day,int month,int year){

    }

    @Deprecated
    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
