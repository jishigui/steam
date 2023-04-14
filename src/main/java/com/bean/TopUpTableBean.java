package com.bean;

/**
 * 充值表
 */
public class TopUpTableBean {

    // 充值金额
    private Integer topMoney;
    // 充值时间
    private String topTime;
    // 充值单号
    private String topId;
    // 用户编号
    private String uID;

    private String out_trade_no;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    @Override
    public String toString() {
        return "TopUpTableBean{" +
                "topMoney=" + topMoney +
                ", topTime='" + topTime + '\'' +
                ", topId='" + topId + '\'' +
                ", uID='" + uID + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                '}';
    }

    public TopUpTableBean(Integer topMoney, String topTime, String topId, String uID, String out_trade_no) {
        this.topMoney = topMoney;
        this.topTime = topTime;
        this.topId = topId;
        this.uID = uID;
        this.out_trade_no = out_trade_no;
    }

    public TopUpTableBean() {
    }

    public Integer getTopMoney() {
        return topMoney;
    }

    public void setTopMoney(Integer topMoney) {
        this.topMoney = topMoney;
    }

    public String getTopTime() {
        return topTime;
    }

    public void setTopTime(String topTime) {
        this.topTime = topTime;
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

}
