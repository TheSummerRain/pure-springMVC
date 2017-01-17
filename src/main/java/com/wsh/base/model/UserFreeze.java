package com.wsh.base.model;

import java.util.Date;

public class UserFreeze {
    private Integer id;

    private Integer userid;

    private String orderno;

    private Date paytime;

    private String wecatno;

    private Integer amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public String getWecatno() {
        return wecatno;
    }

    public void setWecatno(String wecatno) {
        this.wecatno = wecatno == null ? null : wecatno.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}