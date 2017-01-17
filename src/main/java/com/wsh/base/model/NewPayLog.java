package com.wsh.base.model;

import java.util.Date;

public class NewPayLog {
    private Integer payid;

    private String odsystemnum;

    private String transtype;

    private String paychannel;

    private String alipaynum;

    private String wechatpaynum;

    private String mywalletnum;

    private String odstatus;

    private Date createtime;

    private Date comppaytime;

    private Integer transmoney;

    public Integer getPayid() {
        return payid;
    }

    public void setPayid(Integer payid) {
        this.payid = payid;
    }

    public String getOdsystemnum() {
        return odsystemnum;
    }

    public void setOdsystemnum(String odsystemnum) {
        this.odsystemnum = odsystemnum == null ? null : odsystemnum.trim();
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype == null ? null : transtype.trim();
    }

    public String getPaychannel() {
        return paychannel;
    }

    public void setPaychannel(String paychannel) {
        this.paychannel = paychannel == null ? null : paychannel.trim();
    }

    public String getAlipaynum() {
        return alipaynum;
    }

    public void setAlipaynum(String alipaynum) {
        this.alipaynum = alipaynum == null ? null : alipaynum.trim();
    }

    public String getWechatpaynum() {
        return wechatpaynum;
    }

    public void setWechatpaynum(String wechatpaynum) {
        this.wechatpaynum = wechatpaynum == null ? null : wechatpaynum.trim();
    }

    public String getMywalletnum() {
        return mywalletnum;
    }

    public void setMywalletnum(String mywalletnum) {
        this.mywalletnum = mywalletnum == null ? null : mywalletnum.trim();
    }

    public String getOdstatus() {
        return odstatus;
    }

    public void setOdstatus(String odstatus) {
        this.odstatus = odstatus == null ? null : odstatus.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getComppaytime() {
        return comppaytime;
    }

    public void setComppaytime(Date comppaytime) {
        this.comppaytime = comppaytime;
    }

    public Integer getTransmoney() {
        return transmoney;
    }

    public void setTransmoney(Integer transmoney) {
        this.transmoney = transmoney;
    }
}