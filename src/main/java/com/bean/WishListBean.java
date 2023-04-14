package com.bean;

/**
 * 愿望单
 */
public class WishListBean {

    // 游戏编号
    private String gId;
    // 用户编号
    private String uID;

    public WishListBean(String gId, String uID) {
        this.gId = gId;
        this.uID = uID;
    }

    public WishListBean() {
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

    @Override
    public String toString() {
        return "WishList{" +
                "gId='" + gId + '\'' +
                ", uID='" + uID + '\'' +
                '}';
    }
}
