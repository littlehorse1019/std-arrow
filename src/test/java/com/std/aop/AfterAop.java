package com.std.aop;

public class AfterAop {


    public void afterTest (String name) {
        System.out.println("After Test " + name);
    }

    public void afterTest (String name, String user) {
        System.out.println("After Test " + name);
    }

}
