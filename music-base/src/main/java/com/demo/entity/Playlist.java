package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class Playlist implements Serializable {
    private Long id;

    private String playlistTitle;

    private String playlistCover;

    private String playlistLabel;

    private String playlistIntroduction;

    private Long amountOfPlay;

    private Long memberId;

    private Date updateTime;

    private Date createTme;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle == null ? null : playlistTitle.trim();
    }

    public String getPlaylistCover() {
        return playlistCover;
    }

    public void setPlaylistCover(String playlistCover) {
        this.playlistCover = playlistCover == null ? null : playlistCover.trim();
    }

    public String getPlaylistLabel() {
        return playlistLabel;
    }

    public void setPlaylistLabel(String playlistLabel) {
        this.playlistLabel = playlistLabel == null ? null : playlistLabel.trim();
    }

    public String getPlaylistIntroduction() {
        return playlistIntroduction;
    }

    public void setPlaylistIntroduction(String playlistIntroduction) {
        this.playlistIntroduction = playlistIntroduction == null ? null : playlistIntroduction.trim();
    }

    public Long getAmountOfPlay() {
        return amountOfPlay;
    }

    public void setAmountOfPlay(Long amountOfPlay) {
        this.amountOfPlay = amountOfPlay;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTme() {
        return createTme;
    }

    public void setCreateTme(Date createTme) {
        this.createTme = createTme;
    }
}