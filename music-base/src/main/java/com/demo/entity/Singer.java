package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class Singer implements Serializable {
    private Long singerId;

    private String singerName;

    private String singerImg;

    private String nationality;

    private String professional;

    private String magnumOpus;

    private String personalIntroduce;

    private String soloCareer;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName == null ? null : singerName.trim();
    }

    public String getSingerImg() {
        return singerImg;
    }

    public void setSingerImg(String singerImg) {
        this.singerImg = singerImg == null ? null : singerImg.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional == null ? null : professional.trim();
    }

    public String getMagnumOpus() {
        return magnumOpus;
    }

    public void setMagnumOpus(String magnumOpus) {
        this.magnumOpus = magnumOpus == null ? null : magnumOpus.trim();
    }

    public String getPersonalIntroduce() {
        return personalIntroduce;
    }

    public void setPersonalIntroduce(String personalIntroduce) {
        this.personalIntroduce = personalIntroduce == null ? null : personalIntroduce.trim();
    }

    public String getSoloCareer() {
        return soloCareer;
    }

    public void setSoloCareer(String soloCareer) {
        this.soloCareer = soloCareer == null ? null : soloCareer.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}