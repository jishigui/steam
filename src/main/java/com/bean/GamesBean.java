package com.bean;

import java.io.Serializable;

public class GamesBean implements Serializable {

    /**
    * gId:游戏编号
    * gName:游戏名
    * gPrice:单价
    * gDiscount:折扣
    * gSize:游戏大小
    * gImage:图片
    * gContent:游戏描述
    * gTag:游戏标签
     */
    private String gId;
    private String gName;
    private String gPrice;
    private String gDiscount;
    private String gSize;
    private String gImage;
    private String gContent;
    private String gTag;


    public GamesBean() {
    }

    public GamesBean(String gPrice, String gDiscount, String gSize, String gImage, String gContent, String gTag, String gId) {
        this.gPrice = gPrice;
        this.gDiscount = gDiscount;
        this.gSize = gSize;
        this.gImage = gImage;
        this.gContent = gContent;
        this.gTag = gTag;
        this.gId = gId;
    }

    public GamesBean(String gId) {
        this.gId = gId;
    }

    public GamesBean(String gId, String gName, String gPrice, String gDiscount, String gSize, String gImage, String gContent, String gTag) {
        this.gId = gId;
        this.gName = gName;
        this.gPrice = gPrice;
        this.gDiscount = gDiscount;
        this.gSize = gSize;
        this.gImage = gImage;
        this.gContent = gContent;
        this.gTag = gTag;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgPrice() {
        return gPrice;
    }

    public void setgPrice(String gPrice) {
        this.gPrice = gPrice;
    }

    public String getgDiscount() {
        return gDiscount;
    }

    public void setgDiscount(String gDiscount) {
        this.gDiscount = gDiscount;
    }

    public String getgSize() {
        return gSize;
    }

    public void setgSize(String gSize) {
        this.gSize = gSize;
    }

    public String getgImage() {
        return gImage;
    }

    public void setgImage(String gImage) {
        this.gImage = gImage;
    }

    public String getgContent() {
        return gContent;
    }

    public void setgContent(String gContent) {
        this.gContent = gContent;
    }

    public String getgTag() {
        return gTag;
    }

    public void setgTag(String gTag) {
        this.gTag = gTag;
    }


    @Override
    public String toString() {
        return "games{" +
                "gId='" + gId + '\'' +
                ", gName='" + gName + '\'' +
                ", gPrice='" + gPrice + '\'' +
                ", gDiscount='" + gDiscount + '\'' +
                ", gSize='" + gSize + '\'' +
                ", gImage='" + gImage + '\'' +
                ", gContent='" + gContent + '\'' +
                ", gTag='" + gTag + '\'' +
                '}';
    }
}
