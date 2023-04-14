package com.bean;

/**
 * 订单列表
 */
public class OrderListBean {

    // 订单列表编号
    private String osId;
    // 订单编号
    private String oId;
    // 用户编号
    private String uID;
    // 支付订单时间
    private String osPaytime;
    // 支付方式
    private String osPayment;
    // 总金额
    private Double osTotalPrice;
    // 生成订单时间
    private String osCreatetime;
    // 支付状态
    private Integer osPaystatus;

    private String Sernumber;

    public OrderListBean() {
    }

    public OrderListBean(String osId, String oId, String uID, String osPaytime, String osPayment, Double osTotalPrice, String osCreatetime, Integer osPaystatus, String sernumber) {
        this.osId = osId;
        this.oId = oId;
        this.uID = uID;
        this.osPaytime = osPaytime;
        this.osPayment = osPayment;
        this.osTotalPrice = osTotalPrice;
        this.osCreatetime = osCreatetime;
        this.osPaystatus = osPaystatus;
        Sernumber = sernumber;
    }

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getOsPaytime() {
        return osPaytime;
    }

    public void setOsPaytime(String osPaytime) {
        this.osPaytime = osPaytime;
    }

    public String getOsPayment() {
        return osPayment;
    }

    public void setOsPayment(String osPayment) {
        this.osPayment = osPayment;
    }

    public Double getOsTotalPrice() {
        return osTotalPrice;
    }

    public void setOsTotalPrice(Double osTotalPrice) {
        this.osTotalPrice = osTotalPrice;
    }

    public String getOsCreatetime() {
        return osCreatetime;
    }

    public void setOsCreatetime(String osCreatetime) {
        this.osCreatetime = osCreatetime;
    }

    public Integer getOsPaystatus() {
        return osPaystatus;
    }

    public void setOsPaystatus(Integer osPaystatus) {
        this.osPaystatus = osPaystatus;
    }

    public String getSernumber() {
        return Sernumber;
    }

    public void setSernumber(String sernumber) {
        Sernumber = sernumber;
    }


    @Override
    public String toString() {
        return "OrderListBean{" +
                "osId='" + osId + '\'' +
                ", oId='" + oId + '\'' +
                ", uID='" + uID + '\'' +
                ", osPaytime='" + osPaytime + '\'' +
                ", osPayment='" + osPayment + '\'' +
                ", osTotalPrice=" + osTotalPrice +
                ", osCreatetime='" + osCreatetime + '\'' +
                ", osPaystatus=" + osPaystatus +
                ", Sernumber='" + Sernumber + '\'' +
                '}';
    }
}
