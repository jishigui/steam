package com.bean;

/**
 * 评论表
 */

public class CommentFormBean {

    // 游戏编号
    private String gId;
    // 用户编号
    private String uID;
    // 订单列表编号
    private String osId;
    // 评论时间
    private String cDatetime;
    // 评论内容
    private String cContent;
    // 评论星级
    private Integer cRank;
    // 是否匿名
    private Integer cHidden;
    // 评论编号
    private String cId;

    public CommentFormBean(String gId, String uID, String osId, String cDatetime, String cContent, Integer cRank, Integer cHidden, String cId) {
        this.gId = gId;
        this.uID = uID;
        this.osId = osId;
        this.cDatetime = cDatetime;
        this.cContent = cContent;
        this.cRank = cRank;
        this.cHidden = cHidden;
        this.cId = cId;
    }

    public CommentFormBean() {
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }

    public String getcDatetime() {
        return cDatetime;
    }

    public void setcDatetime(String cDatetime) {
        this.cDatetime = cDatetime;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    public Integer getcRank() {
        return cRank;
    }

    public void setcRank(Integer cRank) {
        this.cRank = cRank;
    }

    public Integer getcHidden() {
        return cHidden;
    }

    public void setcHidden(Integer cHidden) {
        this.cHidden = cHidden;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    @Override
    public String toString() {
        return "CommentForm{" +
                "gId='" + gId + '\'' +
                ", uID='" + uID + '\'' +
                ", osId='" + osId + '\'' +
                ", cDatetime='" + cDatetime + '\'' +
                ", cContent='" + cContent + '\'' +
                ", cRank=" + cRank +
                ", cHidden=" + cHidden +
                ", cId='" + cId + '\'' +
                '}';
    }
}
