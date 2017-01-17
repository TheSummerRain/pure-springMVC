package com.wsh.base.model;

import java.util.Date;

public class ArtCollection {
    private Integer cid;

    private Integer userid;

    private Integer artid;

    private Date creadtime;

    //-------------------------
    private String title; //文章标题。
    
    private Integer fromType; //来自"书"Or"维"。

    private String addrPath;  //链接地址。
    
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public String getAddrPath() {
		return addrPath;
	}

	public void setAddrPath(String addrPath) {
		this.addrPath = addrPath;
	}

	public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getArtid() {
        return artid;
    }

    public void setArtid(Integer artid) {
        this.artid = artid;
    }

    public Date getCreadtime() {
        return creadtime;
    }

    public void setCreadtime(Date creadtime) {
        this.creadtime = creadtime;
    }
}