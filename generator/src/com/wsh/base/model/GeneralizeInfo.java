package com.wsh.base.model;

import java.util.Date;

public class GeneralizeInfo {
    private Integer userid;

    private Integer pid;

    private Boolean iselchee;

    private Boolean isagent;

    private Integer pagentid;

    private Short agentstatus;

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

    public Short getAgentstatus() {
        return agentstatus;
    }

    public void setAgentstatus(Short agentstatus) {
        this.agentstatus = agentstatus;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}