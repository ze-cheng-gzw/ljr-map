package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class Album implements Serializable {
    private Long albumId;

    private String albumName;

    private String releaseTime;

    private String albumImg;

    private String albumIntroduction;

    private Date createTime;

    @Override
    public String toString() {
        return "Album{" +
                "albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", albumImg='" + albumImg + '\'' +
                ", albumIntroduction='" + albumIntroduction + '\'' +
                '}';
    }

    private static final long serialVersionUID = 1L;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName == null ? null : albumName.trim();
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime == null ? null : releaseTime.trim();
    }

    public String getAlbumImg() {
        return albumImg;
    }

    public void setAlbumImg(String albumImg) {
        this.albumImg = albumImg == null ? null : albumImg.trim();
    }

    public String getAlbumIntroduction() {
        return albumIntroduction;
    }

    public void setAlbumIntroduction(String albumIntroduction) {
        this.albumIntroduction = albumIntroduction == null ? null : albumIntroduction.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}