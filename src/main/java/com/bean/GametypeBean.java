package com.bean;

public class GametypeBean {

    /*
    * gtId:类型编号
    * gtName:类型名称
     */
    private String gtId;
    private String gtName;

    public GametypeBean() {
    }

    public GametypeBean(String gtId, String gtName) {
        this.gtId = gtId;
        this.gtName = gtName;
    }

    public String getGtId() {
        return gtId;
    }

    public void setGtId(String gtId) {
        this.gtId = gtId;
    }

    public String getGtName() {
        return gtName;
    }

    public void setGtName(String gtName) {
        this.gtName = gtName;
    }

    @Override
    public String toString() {
        return "gametypeBean{" +
                "gtId='" + gtId + '\'' +
                ", gtName='" + gtName + '\'' +
                '}';
    }
}
