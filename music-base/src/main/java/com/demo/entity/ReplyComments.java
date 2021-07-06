package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class ReplyComments implements Serializable {
    private Long id;

    private Long commentsId;

    private Long replyMemberId;

    private String replyMemberName;

    private String replyMemberUrl;

    private String replyContent;

    private Long byReplyMemberId;

    private String byReplyMemberName;

    private String byReplyMemberComments;

    private Integer replyLevel;

    private Long thumbUpNumber;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(Long commentsId) {
        this.commentsId = commentsId;
    }

    public Long getReplyMemberId() {
        return replyMemberId;
    }

    public void setReplyMemberId(Long replyMemberId) {
        this.replyMemberId = replyMemberId;
    }

    public String getReplyMemberName() {
        return replyMemberName;
    }

    public void setReplyMemberName(String replyMemberName) {
        this.replyMemberName = replyMemberName == null ? null : replyMemberName.trim();
    }

    public String getReplyMemberUrl() {
        return replyMemberUrl;
    }

    public void setReplyMemberUrl(String replyMemberUrl) {
        this.replyMemberUrl = replyMemberUrl == null ? null : replyMemberUrl.trim();
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent == null ? null : replyContent.trim();
    }

    public Long getByReplyMemberId() {
        return byReplyMemberId;
    }

    public void setByReplyMemberId(Long byReplyMemberId) {
        this.byReplyMemberId = byReplyMemberId;
    }

    public String getByReplyMemberName() {
        return byReplyMemberName;
    }

    public void setByReplyMemberName(String byReplyMemberName) {
        this.byReplyMemberName = byReplyMemberName == null ? null : byReplyMemberName.trim();
    }

    public String getByReplyMemberComments() {
        return byReplyMemberComments;
    }

    public void setByReplyMemberComments(String byReplyMemberComments) {
        this.byReplyMemberComments = byReplyMemberComments == null ? null : byReplyMemberComments.trim();
    }

    public Integer getReplyLevel() {
        return replyLevel;
    }

    public void setReplyLevel(Integer replyLevel) {
        this.replyLevel = replyLevel;
    }

    public Long getThumbUpNumber() {
        return thumbUpNumber;
    }

    public void setThumbUpNumber(Long thumbUpNumber) {
        this.thumbUpNumber = thumbUpNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}