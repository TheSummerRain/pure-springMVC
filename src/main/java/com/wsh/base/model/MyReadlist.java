package com.wsh.base.model;

import java.util.Date;

public class MyReadlist {
    private Integer id;

    private Integer userid;

    private Date createtime;

    private String readlist;

    private String wishes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getReadlist() {
        return readlist;
    }

    public void setReadlist(String readlist) {
        this.readlist = readlist == null ? null : readlist.trim();
    }

    public String getWishes() {
        return wishes;
    }

    public void setWishes(String wishes) {
        this.wishes = wishes == null ? null : wishes.trim();
    }
}