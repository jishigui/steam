package com.util;

public class JsonUtil {

    /**
     * 代表错误代码
     *   0表示正常
     *   非0表示其它错误
     */
    private int code;
    /**
     * 表示错误信息的文字描述 如成功，系统错误等等
     */
    private String msg;

    /**
     * 服务端返回给客户端的数据 如：查询结果(用户信息列表)
     */
    private Object data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
