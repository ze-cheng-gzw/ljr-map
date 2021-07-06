package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class Singer implements Serializable {
    private Long singerId;

    private String singerName;

    private String firstLetter;

    private Integer singerSex;

    private String singerImg;

    private String nationality;

    private String personalIntroduce;

    private Date createTime;

    @Override
    public String toString() {
        return "Singer{" +
                "singerId=" + singerId +
                ", singerName='" + singerName + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                ", singerSex=" + singerSex +
                ", singerImg='" + singerImg + '\'' +
                ", nationality='" + nationality + '\'' +
                ", personalIntroduce='" + personalIntroduce + '\'' +
                ", createTime=" + createTime +
                '}';
    }

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

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public Integer getSingerSex() {
        return singerSex;
    }

    public void setSingerSex(Integer singerSex) {
        this.singerSex = singerSex;
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

    public String getPersonalIntroduce() {
        return personalIntroduce;
    }

    public void setPersonalIntroduce(String personalIntroduce) {
        this.personalIntroduce = personalIntroduce == null ? null : personalIntroduce.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}