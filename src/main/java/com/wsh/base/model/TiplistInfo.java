package com.wsh.base.model;


import java.util.Date;

public class TiplistInfo {
    private Integer tipId;

    private Integer bookId;

    private Integer artId;

    private String tipInfo1;

    private String tipInfo2;

    private String tipInfo3;

    private String tipInfo4;

    private String tipInfo5;

    private Date time;

    private Integer operaId;

    public Integer getTipId() {
        return tipId;
    }

    public void setTipId(Integer tipId) {
        this.tipId = tipId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public String getTipInfo1() {
        return tipInfo1;
    }

    public void setTipInfo1(String tipInfo1) {
        this.tipInfo1 = tipInfo1 == null ? null : tipInfo1.trim();
    }

    public String getTipInfo2() {
        return tipInfo2;
    }

    public void setTipInfo2(String tipInfo2) {
        this.tipInfo2 = tipInfo2 == null ? null : tipInfo2.trim();
    }

    public String getTipInfo3() {
        return tipInfo3;
    }

    public void setTipInfo3(String tipInfo3) {
        this.tipInfo3 = tipInfo3 == null ? null : tipInfo3.trim();
    }

    public String getTipInfo4() {
        return tipInfo4;
    }

    public void setTipInfo4(String tipInfo4) {
        this.tipInfo4 = tipInfo4 == null ? null : tipInfo4.trim();
    }

    public String getTipInfo5() {
        return tipInfo5;
    }

    public void setTipInfo5(String tipInfo5) {
        this.tipInfo5 = tipInfo5 == null ? null : tipInfo5.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getOperaId() {
        return operaId;
    }

    public void setOperaId(Integer operaId) {
        this.operaId = operaId;
    }
}