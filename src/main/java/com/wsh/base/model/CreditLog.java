package com.wsh.base.model;

import java.util.Date;
/**
 * 
 * @package : com.wsh.base.model
 * @ClassName: CreditLog
 * @Description: 积分 信息 
 * @author Wally
 * @date 2016年1月9日 下午2:50:45
 * @modify Wally
 * @modifyDate 2016年1月9日 下午2:50:45
 */
public class CreditLog {
    private Integer creditId;  //这个字段没有任何业务需要。只是为了增加查询速度的索引字段。

    private Integer userId;

    private Integer spCredit;

    private String intro;

    private Integer income; //收入

    private Integer expend; //消費
    
    //这就是用户最新的剩余积分。拿这个积分，就可以作为新的记录剩余积分的基础。
    //select sp_credit from credit_log where user_id=用户ＩＤ order by createtime desc LIMIT 1; 
    private Date createtime; //主要的判断剩余积分的规则，都是依靠这个字段的最后时间来说的。

    private Integer channel;

    public Integer getCreditId() {
        return creditId;
    }

    public void setCreditId(Integer creditId) {
        this.creditId = creditId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSpCredit() {
        return spCredit;
    }

    public void setSpCredit(Integer spCredit) {
        this.spCredit = spCredit;
    }

 

    public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
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