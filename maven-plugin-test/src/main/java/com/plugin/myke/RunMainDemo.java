package com.plugin.myke;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * main 执行类
 */
public class RunMainDemo {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            System.out.println(Calendar.getInstance().getTime() + ":" + i);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}