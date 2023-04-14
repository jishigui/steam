package com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    Properties properties=new Properties();
    public PropertiesUtil() throws FileNotFoundException {
        try {
            properties.load(new FileInputStream("src\\re.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDriver(){
       if(properties.containsKey("driver")){
           return properties.get("driver").toString();
       }else {
           return null;
       }
    }

    public String getUrl(){
        if(properties.containsKey("url")){
            return properties.get("url").toString();
        }else {
            return null;
        }
    }

    public String getUsername(){
        if(properties.containsKey("username")){
            return properties.get("username").toString();
        }else {
            return null;
        }
    }

    public String getPassword(){
        if(properties.containsKey("password")){
            return properties.get("password").toString();
        }else {
            return null;
        }
    }

//    public static void main(String[] args) throws Exception {
//        PropertiesUtil propertiesUtil=null;
//        try {
//            propertiesUtil=new PropertiesUtil();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println(propertiesUtil.getDriver());
//        System.out.println(propertiesUtil.getUrl());
//        System.out.println(propertiesUtil.getUsername());
//        System.out.println(propertiesUtil.getPassword());
//
//        String url=propertiesUtil.getUrl();
//        String name=propertiesUtil.getUsername();
//        String pwd=propertiesUtil.getPassword();
//        JdbcUtil jdbcUtil=new JdbcUtil();
//        JdbcUtil.getConnection();

//    }
}
