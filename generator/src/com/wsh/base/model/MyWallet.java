package com.wsh.base.model;

import java.util.Date;

public class MyWallet {
    private Integer walid;

    private Integer fromuserid;

    private Integer touserid;

    private String cmtype;

    private Integer money;

    private Date createtime;

    private String eptype;

    private String epchannel;

    private String eporderno;

    private String channeltransno;

    private String sourcedes;

    public Integer getWalid() {
        return walid;
    }

    public void setWalid(Integer walid) {
        this.walid = walid;
    }

    public Integer getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Integer fromuserid) {
        this.fromuserid = fromuserid;
    }

    public Integer getTouserid() {
        return touserid;
    }

    public void setTouserid(Integer touserid) {
        this.touserid = touserid;
    }

    public String getCmtype() {
        return cmtype;
    }

    public void setCmtype(String cmtype) {
        this.cmtype = cmtype == null ? null : cmtype.trim();
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

    public String getEptype() {
        return eptype;
    }

    public void setEptype(String eptype) {
        this.eptype = eptype == null ? null : eptype.trim();
    }

    public String getEpchannel() {
        return epchannel;
    }

    public void setEpchannel(String epchannel) {
        this.epchannel = epchannel == null ? null : epchannel.trim();
    }

    public String getEporderno() {
        return eporderno;
    }

    public void setEporderno(String eporderno) {
        this.eporderno = eporderno == null ? null : eporderno.trim();
    }

    public String getChanneltransno() {
        return channeltransno;
    }

    public void setChanneltransno(String channeltransno) {
        this.channeltransno = channeltransno == null ? null : channeltransno.trim();
    }

    public String getSourcedes() {
        return sourcedes;
    }

    public void setSourcedes(String sourcedes) {
        this.sourcedes = sourcedes == null ? null : sourcedes.trim();
    }
}