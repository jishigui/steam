package com.bean;

public class PublishBean {

    /*
    * aId:管理员编号
    * gId:游戏
    * pDatetime:发布时间
     */
    private String aId;
    private String gId;
    private String pDatetime;

    public PublishBean(String aId, String gId, String pDatetime) {
        this.aId = aId;
        this.gId = gId;
        this.pDatetime = pDatetime;
    }

    public PublishBean() {
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getpDatetime() {
        return pDatetime;
    }

    public void setpDatetime(String pDatetime) {
        this.pDatetime = pDatetime;
    }
}
