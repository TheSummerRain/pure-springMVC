package com.wsh.base.model;


import java.util.Date;

public class ArticleInfo {
    private Integer artId;

    private Integer bookId;

    private String artTitle;

    private String artAuthor;

    private String artUrl;

    private Integer artStatus;

    private Date artCreattime;

    private Date artPublishtime;

    private Boolean isfree;

    private Integer price;
    
    private String freeVedio;

    private String vipVedio;

    private String artIntro;

  //2016.1.7 add by Wally;文章类型，区分是:0-维（不带视频音频），还是:1-书（带视频）。
    private Integer artType; 
    //2016.1.7 add by Wally;文章标签，"1简介","2作品","3推荐","4课堂","5公益","6荐书"
    private Integer artLabel;
    
    private String wei_picUrl; //维 文 的 缩略图 图片路径。
    
    private String contentFree; //add by Wally .免费文章内容。
    
	public String getContentFree() {
		return contentFree;
	}

	public void setContentFree(String contentFree) {
		this.contentFree = contentFree;
	}

    
    public String getWei_picUrl() {
		return wei_picUrl;
	}
    
    public void setWei_picUrl(String wei_picUrl) {
		this.wei_picUrl = wei_picUrl;
	}

	public Integer getArtLabel() {
		return artLabel;
	}

	public void setArtLabel(Integer artLabel) {
		this.artLabel = artLabel;
	}

	private String content;

    
    
    public Integer getArtType() {
		return artType;
	}

	public void setArtType(Integer artType) {
		this.artType = artType;
	}

	public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getArtTitle() {
        return artTitle;
    }

    public void setArtTitle(String artTitle) {
        this.artTitle = artTitle == null ? null : artTitle.trim();
    }

    public String getArtAuthor() {
        return artAuthor;
    }

    public void setArtAuthor(String artAuthor) {
        this.artAuthor = artAuthor == null ? null : artAuthor.trim();
    }

    public String getArtUrl() {
        return artUrl;
    }

    public void setArtUrl(String artUrl) {
        this.artUrl = artUrl == null ? null : artUrl.trim();
    }

    public Integer getArtStatus() {
        return artStatus;
    }

    public void setArtStatus(Integer artStatus) {
        this.artStatus = artStatus;
    }

    public Date getArtCreattime() {
        return artCreattime;
    }

    public void setArtCreattime(Date artCreattime) {
        this.artCreattime = artCreattime;
    }

    public Date getArtPublishtime() {
        return artPublishtime;
    }

    public void setArtPublishtime(Date artPublishtime) {
        this.artPublishtime = artPublishtime;
    }

    public Boolean getIsfree() {
        return isfree;
    }

    public void setIsfree(Boolean isfree) {
        this.isfree = isfree;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getFreeVedio() {
		return freeVedio;
	}

	public void setFreeVedio(String freeVedio) {
		this.freeVedio = freeVedio;
	}

	public String getVipVedio() {
		return vipVedio;
	}

	public void setVipVedio(String vipVedio) {
		this.vipVedio = vipVedio;
	}

	public String getArtIntro() {
		return artIntro;
	}

	public void setArtIntro(String artIntro) {
		this.artIntro = artIntro;
	}
    
}