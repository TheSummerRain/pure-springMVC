package com.wsh.base.model;


import java.util.Date;

/**
 * 
 * @package : com.wsh.base.model
 * @ClassName: WeibiLog
 * @Description: 维币 流水 日志表。
 * @author Wally
 * @date 2016年1月9日 下午6:02:27
 * @modify Wally
 * @modifyDate 2016年1月9日 下午6:02:27
 */
public class WeibiLog {
    private Integer weibId;

    private Integer userId;

    private Integer spWeibi;

    private String intro;

    private Integer income;

    private Integer expend;

    private Date createtime;  //关注这个字段，很重要哦。

    private Integer channel;
    
    private String orderno; //订单号。2016.1.11 Wally Add. 假如是充值，这里应该有值，需要与订单支付表相对应。
    

    public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Integer getWeibId() {
        return weibId;
    }

    public void setWeibId(Integer weibId) {
        this.weibId = weibId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSpWeibi() {
        return spWeibi;
    }

    public void setSpWeibi(Integer spWeibi) {
        this.spWeibi = spWeibi;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getExpend() {
        return expend;
    }

    public void setExpend(Integer expend) {
        this.expend = expend;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}