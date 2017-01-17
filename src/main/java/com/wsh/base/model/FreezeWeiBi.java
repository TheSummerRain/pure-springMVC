package com.wsh.base.model;

import java.util.Date;

/**
 * 
 * @package : com.wsh.base.model
 * @ClassName: FreezeWeiBi
 * @Description: WB冻结编号
 * @author Wally
 * @date 2016年1月11日 下午8:42:11
 * @modify Wally
 * @modifyDate 2016年1月11日 下午8:42:11
 */
public class FreezeWeiBi {
    private Integer freId;

    private Integer userId;

    private Integer freMoney;

    private Date createtime;

    private Integer status;

    private Integer weibiId;

    public Integer getFreId() {
        return freId;
    }

    public void setFreId(Integer freId) {
        this.freId = freId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFreMoney() {
        return freMoney;
    }

    public void setFreMoney(Integer freMoney) {
        this.freMoney = freMoney;
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

    public Integer getWeibiId() {
        return weibiId;
    }

    public void setWeibiId(Integer weibiId) {
        this.weibiId = weibiId;
    }
}