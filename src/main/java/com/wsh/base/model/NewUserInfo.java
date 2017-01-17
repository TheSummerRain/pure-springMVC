package com.wsh.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: newUserInfo
 * @Description: 新的用户数据结构。启用这个数据结构。
 * @author Wally
 * @date 2016年12月12日 下午3:06:14
 * @modify Wally
 * @modifyDate 2016年12月12日 下午3:06:14
 */

public class NewUserInfo extends UserInfo implements Serializable{
	
	private static final long serialVersionUID = 163178525060669616L;

	private Integer id;

    private String thirdid;

    private Integer viplv;

    private String ossheadpic;

    private String mobile;

    private Integer sex;

    private Date vipstarttime;

    private Date vipendtime;

    private String password;

    private String userstatus;

    private Integer third;

    private Date lastlandingtime;

    private Date createdtime;

    private String wxunionid;

    private String openid;

    private String nickname;

    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThirdid() {
        return thirdid;
    }

    public void setThirdid(String thirdid) {
        this.thirdid = thirdid == null ? null : thirdid.trim();
    }

    public Integer getViplv() {
        return viplv;
    }

    public void setViplv(Integer viplv) {
        this.viplv = viplv;
    }

    public String getOssheadpic() {
        return ossheadpic;
    }

    public void setOssheadpic(String ossheadpic) {
        this.ossheadpic = ossheadpic == null ? null : ossheadpic.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }


    public Date getVipstarttime() {
        return vipstarttime;
    }

    public void setVipstarttime(Date vipstarttime) {
        this.vipstarttime = vipstarttime;
    }

    public Date getVipendtime() {
        return vipendtime;
    }

    public void setVipendtime(Date vipendtime) {
        this.vipendtime = vipendtime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus == null ? null : userstatus.trim();
    }

    public Integer getThird() {
        return third;
    }

    public void setThird(Integer third) {
        this.third = third;
    }

    public Date getLastlandingtime() {
        return lastlandingtime;
    }

    public void setLastlandingtime(Date lastlandingtime) {
        this.lastlandingtime = lastlandingtime;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public String getWxunionid() {
        return wxunionid;
    }

    public void setWxunionid(String wxunionid) {
        this.wxunionid = wxunionid == null ? null : wxunionid.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

   
}