package com.wsh.base.model;

//推广员基础信息实体
public class PromoterInfo {
    private Integer userId;

    private String imgCode;

    private Integer userNum;

    private Integer vipuserNum;

    private Integer incomeWb;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode == null ? null : imgCode.trim();
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getVipuserNum() {
        return vipuserNum;
    }

    public void setVipuserNum(Integer vipuserNum) {
        this.vipuserNum = vipuserNum;
    }

    public Integer getIncomeWb() {
        return incomeWb;
    }

    public void setIncomeWb(Integer incomeWb) {
        this.incomeWb = incomeWb;
    }
}