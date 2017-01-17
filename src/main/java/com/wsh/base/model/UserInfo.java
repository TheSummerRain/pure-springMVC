package com.wsh.base.model;


import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class UserInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;

    private String vipId;

    private String headPic;

    private String password; //预留

    private String mobile;

    private String nickname;

    private Integer gender;

    private Date vipStartdate;

    private Date vipEnddate;

    private String payPassword;

    //-------2016.1.13 Wally ADD-----这些值只在更新银行卡信息和 收货地址信息是才会用到。---------
    private Date birthdate; //生日
    private String banktype; //银行代码-代码-代码。
    private String bankNo; //银行代码。
    private String userName; //开户行名称。
    private String subBank; //支行。
    
    private String preAddr; //地址前缀。省/市/区         // 以后统一用微信的吧。
    private String addr;
    private String acceptName;//收货人姓名。防止与username对撞。可能是亲人朋友。
    private String postcode; //邮编。
    //-------------------------------------
    
    
    //---------代理商-----------------------------
    private Boolean agent;   //是否是代理商
    private Date agentStartDate;
    private Date agentEndDate;
    private Integer agentpre; //上级代理商ID。
    
    //WeChat
    private Integer pId;
    private String openid;
    private String province;
    private String city;
    private String country;
    private String unionid;
    private Boolean isMaster;
    private String qrcode;
    
    public String getSubBank() {
		return subBank;
	}

	public void setSubBank(String subBank) {
		this.subBank = subBank;
	}

	public String getAcceptName() {
		return acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPreAddr() {
		return preAddr;
	}

	public void setPreAddr(String preAddr) {
		this.preAddr = preAddr;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

 
    public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = StringUtils.trim(headPic);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = StringUtils.trim(userName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password =StringUtils.trim(password);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile =StringUtils.trim(mobile);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname =StringUtils.trim(nickname);
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = StringUtils.trim(addr);
    }

    public Date getVipStartdate() {
        return vipStartdate;
    }

    public void setVipStartdate(Date vipStartdate) {
        this.vipStartdate = vipStartdate;
    }

    public Date getVipEnddate() {
        return vipEnddate;
    }

    public void setVipEnddate(Date vipEnddate) {
        this.vipEnddate = vipEnddate;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword =StringUtils.trim(payPassword);
    }

    
    
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	

	public Boolean getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(Boolean isMaster) {
		this.isMaster = isMaster;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Boolean getAgent() {
		return agent;
	}

	public void setAgent(Boolean agent) {
		this.agent = agent;
	}

	public Date getAgentStartDate() {
		return agentStartDate;
	}

	public void setAgentStartDate(Date agentStartDate) {
		this.agentStartDate = agentStartDate;
	}

	public Date getAgentEndDate() {
		return agentEndDate;
	}

	public void setAgentEndDate(Date agentEndDate) {
		this.agentEndDate = agentEndDate;
	}

	public Integer getAgentpre() {
		return agentpre;
	}

	public void setAgentpre(Integer agentpre) {
		this.agentpre = agentpre;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", vipId=" + vipId + ", headPic="
				+ headPic + ", userName=" + userName + ", password=" + password
				+ ", mobile=" + mobile + ", nickname=" + nickname + ", gender="
				+ gender + ", addr=" + addr + ", vipStartdate=" + vipStartdate
				+ ", vipEnddate=" + vipEnddate + ", payPassword=" + payPassword
				+ ", openid=" + openid + ", province=" + province + ", city="
				+ city + ", country=" + country + ", unionid=" + unionid + "]";
	}

	public UserInfo(String openid) {
		super();
		this.openid = openid;
	}
	public UserInfo() {
		super();
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
}