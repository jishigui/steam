package com.bean;

public class UserBean {
    /**
     * uId:用户编号
     * uName:用户名
     * unick:用户昵称
     * uTel:手机号
     * uMail:用户邮箱
     * uRegTime:注册时间
     * uStatus:账户状态
     * uIden:身份证号
     * uBalance:余额
     */

    private String uID;
    private String uName;
    private String uPwd;
    private String uNick;
    private String uTel;
    private String uMail;
    private String uRegTime;
    private int uStatus;
    private String uIden;
    private double uBalance;

    public UserBean() {
    }

    public UserBean(String uID, String uName, String uPwd, String uNick, String uTel, String uMail, String uRegTime, int uStatus, String uIden, double uBalance) {
        this.uID = uID;
        this.uName = uName;
        this.uPwd = uPwd;
        this.uNick = uNick;
        this.uTel = uTel;
        this.uMail = uMail;
        this.uRegTime = uRegTime;
        this.uStatus = uStatus;
        this.uIden = uIden;
        this.uBalance = uBalance;
    }

    public String getuID() {
        return uID;
    }
    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String getuTel() {
        return uTel;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel;
    }

    public String getuMail() {
        return uMail;
    }

    public void setuMail(String uMail) {
        this.uMail = uMail;
    }

    public String getuRegTime() {
        return uRegTime;
    }

    public void setuRegTime(String uRegTime) {
        this.uRegTime = uRegTime;
    }

    public int getuStatus() {
        return uStatus;
    }

    public void setuStatus(int uStatus) {
        this.uStatus = uStatus;
    }

    public String getuIden() {
        return uIden;
    }

    public void setuIden(String uIden) {
        this.uIden = uIden;
    }

    public double getuBalance() {
        return uBalance;
    }

    public void setuBalance(double uBalance) {
        this.uBalance = uBalance;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "uID='" + uID + '\'' +
                ", uName='" + uName + '\'' +
                ", uPwd='" + uPwd + '\'' +
                ", uNick='" + uNick + '\'' +
                ", uTel='" + uTel + '\'' +
                ", uMail='" + uMail + '\'' +
                ", uRegTime='" + uRegTime + '\'' +
                ", uStatus='" + uStatus + '\'' +
                ", uIden='" + uIden + '\'' +
                ", uBalance='" + uBalance + '\'' +
                '}';
    }
}
