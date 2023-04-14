package com.bean;

public class GameTagBean {

    /*
    * gId:游戏编号
    * gtId:类型编号
    * gtName:类型名称
     */
    private String gId;
    private String gtId;
    private String gtName;

    public GameTagBean() {
    }

    public GameTagBean(String gId, String gtId, String gtName) {
        this.gId = gId;
        this.gtId = gtId;
        this.gtName = gtName;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
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
        return "GameTagBean{" +
                "gId='" + gId + '\'' +
                ", gtId='" + gtId + '\'' +
                ", gtName='" + gtName + '\'' +
                '}';
    }
}
