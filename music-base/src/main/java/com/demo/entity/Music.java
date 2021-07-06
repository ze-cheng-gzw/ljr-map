package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class Music implements Serializable {
    private Long musicId;

    private String musicName;

    private Long singerId;

    private Long albumId;

    private String coverUrl;

    private Integer toVip;

    private String musicUrl;

    private String musicLyrics;

    private String musicTimeLength;

    private Date createTime;

    @Override
    public String toString() {
        return "Music{" +
                "musicId=" + musicId +
                ", musicName='" + musicName + '\'' +
                ", singerId=" + singerId +
                ", albumId=" + albumId +
                ", coverUrl='" + coverUrl + '\'' +
                ", toVip=" + toVip +
                ", musicUrl='" + musicUrl + '\'' +
                ", musicLyrics='" + musicLyrics + '\'' +
                ", musicTimeLength='" + musicTimeLength + '\'' +
                '}';
    }

    private static final long serialVersionUID = 1L;

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName == null ? null : musicName.trim();
    }

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl == null ? null : coverUrl.trim();
    }

    public Integer getToVip() {
        return toVip;
    }

    public void setToVip(Integer toVip) {
        this.toVip = toVip;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl == null ? null : musicUrl.trim();
    }

    public String getMusicLyrics() {
        return musicLyrics;
    }

    public void setMusicLyrics(String musicLyrics) {
        this.musicLyrics = musicLyrics == null ? null : musicLyrics.trim();
    }

    public String getMusicTimeLength() {
        return musicTimeLength;
    }

    public void setMusicTimeLength(String musicTimeLength) {
        this.musicTimeLength = musicTimeLength == null ? null : musicTimeLength.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}