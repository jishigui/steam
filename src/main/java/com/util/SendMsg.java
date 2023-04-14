package com.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Set;

public class SendMsg {


    /**
     *
     * @param mobilePhone 接收者的手机号
     * @return  返回程序内部的6位的短信验证码，返回 999999表示短信发送失败
     */
    public static int getCode(String mobilePhone){
        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
        restAPI.init("app.cloopen.com", "8883");
        // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
        restAPI.setAccount("8a216da87ce04099017ce47cd612007c", "f0d1593f49b34dc8924ab93f7250e868");
        restAPI.setAppId("8a216da87ce04099017ce47cd7460082");    // 请使用管理控制台中已创建应用的APPID。
        //产生一 个随机的6位数字
        String code="";
        for(int i=0;i<6;i++){
            code=code+(int)(Math.random()*10);
        }
        //第一个参数为接口的手机号(填入测试号)
        //第二个参数填入模板编号
        result = restAPI.sendTemplateSMS(mobilePhone,"1" ,new String[]{String.valueOf(code),"5"});//5代表5分钟

        System.out.println("短信发送结果=" + result);//服务端的返回结果
        if("000000".equals(result.get("statusCode"))){//短信发送成功手，statusCode值为为 000000
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){//拿到短信发送结果返回的详情信息，包含 dateCreated=短信的发送时间
                                                                //smsMessageSid 短信发送的消息号
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
            return Integer.parseInt(code);
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            return  999999;
        }


    }
}
