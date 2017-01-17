package com.wsh.base.model;

/**
 * Created by Andy_Liu on 2016/11/25.
 */
public class Card {
    private Integer id;
    private String num;
    private String keyt;
    private boolean valid;
    private Integer owner;
    private Integer pId;
    private String ownerName;
    private String ownerMobile;


    private String verification;
    private String flag;

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getKeyt() {
        return keyt;
    }

    public void setKeyt(String keyt) {
        this.keyt = keyt;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", pId='" + pId + '\'' +
                ", keyt='" + keyt + '\'' +
                ", verification='" + verification + '\'' +
                ", valid=" + valid +
                ", flag=" + flag +
                ", owner=" + owner +
                ", ownerName='" + ownerName + '\'' +
                ", ownerMobile='" + ownerMobile + '\'' +
                '}';
    }
}
