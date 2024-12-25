package com.seasugar.basic;

import java.io.InputStream;

public class ClassPathDemo {
    public static void main(String[] args) {
        Package pkg = ClassPathDemo.class.getPackage();
        String packagePath = pkg.getName();
        System.out.println("当前类的包路径: " + packagePath);

//        String resourceName = "config.properties";
//        String resourceName = "application.yml";
//        String resourceName = "static/abc.icon";
        String resourceName = "abc.icon";
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
        if (resourceAsStream!= null) {
            System.out.println("classpath 中资源 " + resourceName + " 的路径为: " + ClassLoader.getSystemClassLoader().getResource(resourceName));
        } else {
            System.out.println("未在 classpath 中找到指定资源: " + resourceName);
        }
    }
}
