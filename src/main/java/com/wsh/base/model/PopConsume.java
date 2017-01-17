package com.wsh.base.model;

import java.util.Date;

/**
 * 
 * @package : com.wsh.base.model
 * @ClassName: PopConsume
 * @Description: 推广消费 记录实体【只记录减少数据】
 * @author Wally
 * @date 2016年2月1日 下午12:00:56
 * @modify Wally
 * @modifyDate 2016年2月1日 下午12:00:56
 */
public class PopConsume {
    private Integer id;

    private Integer userid;

    private String opt;  //操作类型。提现消费。

    private Integer money; //消费金额。

    private Date createtime;

    private Integer status; //预留

    private Integer orderid; //预留

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

 

    public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }
}