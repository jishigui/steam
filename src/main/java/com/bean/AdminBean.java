package com.bean;



public class AdminBean {
    /**
     * aId:管理员编号
     * aName:管理员用户名
     * aPwd:管理员密码
     * aTel:管理员电话
     */
    String aId;
    String aName;
    String aPwd;
    String aTel;

    public AdminBean() {
    }

    public AdminBean(String aId, String aName, String aPwd, String aTel) {
        this.aId = aId;
        this.aName = aName;
        this.aPwd = aPwd;
        this.aTel = aTel;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaPwd() {
        return aPwd;
    }

    public void setaPwd(String aPwd) {
        this.aPwd = aPwd;
    }

    public String getaTel() {
        return aTel;
    }

    public void setaTel(String aTel) {
        this.aTel = aTel;
    }


    @Override
    public String toString() {
        return "adminId{" +
                "aId='" + aId + '\'' +
                ", aName='" + aName + '\'' +
                ", aPwd='" + aPwd + '\'' +
                ", aTel='" + aTel + '\'' +
                '}';
    }
}

