package com.wsh.base.model;

import java.util.Date;

public class UserComment {
	
    private Integer cid;

    private Integer artid;

    private Integer userid;

    private String headpic;

    private Date createtime;

    private Boolean isnice;

    private String ctext;

    private Integer countpra;

    private String nickname;
    
    private String arttitle;

    private Boolean plZan;   //默认false;  当前用户是否对该条记录点赞。
    private Integer countZan;  //点赞总数。后期从数据库查询。定期从redis写入数据库。
    

	public Boolean getPlZan() {
		return plZan;
	}

	public void setPlZan(Boolean plZan) {
		this.plZan = plZan;
	}

	public Integer getCountZan() {
		return countZan;
	}

	public void setCountZan(Integer countZan) {
		this.countZan = countZan;
	}


	public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getArtid() {
        return artid;
    }

    public void setArtid(Integer artid) {
        this.artid = artid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic == null ? null : headpic.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Boolean getIsnice() {
        return isnice;
    }

    public void setIsnice(Boolean isnice) {
        this.isnice = isnice;
    }

    public String getCtext() {
        return ctext;
    }

    public void setCtext(String ctext) {
        this.ctext = ctext == null ? null : ctext.trim();
    }

    public Integer getCountpra() {
        return countpra;
    }

    public void setCountpra(Integer countpra) {
        this.countpra = countpra;
    }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getArttitle() {
		return arttitle;
	}

	public void setArttitle(String arttitle) {
		this.arttitle = arttitle;
	}

 
}