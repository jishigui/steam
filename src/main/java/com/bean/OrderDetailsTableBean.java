package com.bean;

/**
 * 订单详情表
 */
public class OrderDetailsTableBean {
    // 订单列表编号
    private  String osId;
    // 订单编号
    private String oId;
    // 订单折扣
    private Double oDiscount;

    @Override
    public String toString() {
        return "OrderDetailsTableBean{" +
                "osId='" + osId + '\'' +
                ", oId='" + oId + '\'' +
                ", oDiscount=" + oDiscount +
                ", oPrice=" + oPrice +
                ", oName='" + oName + '\'' +
                ", uID='" + uID + '\'' +
                '}';
    }

    // 商品单价
    private Double oPrice;
    // 商品名称
    private String oName;

    private  String uID;

    public OrderDetailsTableBean() {
    }


    public OrderDetailsTableBean(String osId, String oId, Double oDiscount, Double oPrice, String oName, String uID) {
        this.osId = osId;
        this.oId = oId;
        this.oDiscount = oDiscount;
        this.oPrice = oPrice;
        this.oName = oName;
        this.uID = uID;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public Double getoDiscount() {
        return oDiscount;
    }

    public void setoDiscount(Double oDiscount) {
        this.oDiscount = oDiscount;
    }

    public Double getoPrice() {
        return oPrice;
    }

    public void setoPrice(Double oPrice) {
        this.oPrice = oPrice;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
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
}
