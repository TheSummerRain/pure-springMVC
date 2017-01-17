package com.wsh.base.model;


import java.util.Date;
/**
 * 
 * @package : com.wsh.base.model
 * @ClassName: PayLog
 * @Description: 订单日志，支付流水表;  涉及到钱 时，才会 用到这个实体。如果只有维币，则只需要变动wb就行了。
 * @author Wally
 * @date 2016年1月11日 下午2:55:36
 * @modify Wally
 * @modifyDate 2016年1月11日 下午2:55:36
 */
public class PayLog {
    private Integer payid;

    private Integer userId;

    private Date paydate;

    private String bustype;

    private Integer transmoney;

    private String orderno;

    private String transactionId;

    private String alipayId;

    private Integer status;

    public Integer getPayid() {
        return payid;
    }

    public void setPayid(Integer payid) {
        this.payid = payid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public String getBustype() {
        return bustype;
    }

    public void setBustype(String bustype) {
        this.bustype = bustype == null ? null : bustype.trim();
    }

    public Integer getTransmoney() {
        return transmoney;
    }

    public void setTransmoney(Integer transmoney) {
        this.transmoney = transmoney;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public String getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(String alipayId) {
        this.alipayId = alipayId == null ? null : alipayId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}