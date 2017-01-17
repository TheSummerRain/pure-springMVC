package com.wsh.base.model;

import java.util.Date;

/**
 * 
 * @ClassName: GeneralizeInfo
 * @Description: 三级分销，关系表数据结构。
 * @author Wally
 * @date 2016年12月12日 下午3:16:00
 * @modify Wally
 * @modifyDate 2016年12月12日 下午3:16:00
 */

public class GeneralizeInfo {
	
    private Integer userid;

    private Integer pid;

    private Boolean iselchee;

    private Boolean isagent;

    private Integer pagentid;

    private int agentstatus;

    private Date createtime;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Boolean getIselchee() {
        return iselchee;
    }

    public void setIselchee(Boolean iselchee) {
        this.iselchee = iselchee;
    }

    public Boolean getIsagent() {
        return isagent;
    }

    public void setIsagent(Boolean isagent) {
        this.isagent = isagent;
    }

    public Integer getPagentid() {
        return pagentid;
    }

    public void setPagentid(Integer pagentid) {
        this.pagentid = pagentid;
    }


    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	public int getAgentstatus() {
		return agentstatus;
	}

	public void setAgentstatus(int agentstatus) {
		this.agentstatus = agentstatus;
	}
}